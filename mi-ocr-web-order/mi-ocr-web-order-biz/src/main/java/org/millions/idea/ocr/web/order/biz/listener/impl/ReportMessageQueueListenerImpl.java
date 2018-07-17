/***
 * @pName mi-ocr-web-order
 * @name ReportMessageQueueListenerImpl
 * @user HongWei
 * @date 2018/7/8
 * @desc
 */
package org.millions.idea.ocr.web.order.biz.listener.impl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.millions.idea.ocr.web.common.utility.json.JsonUtil;
import org.millions.idea.ocr.web.order.biz.listener.IPayMessageQueueListener;
import org.millions.idea.ocr.web.order.biz.order.impl.BaseAddOperationImpl;
import org.millions.idea.ocr.web.order.entity.agent.PayParam;
import org.millions.idea.ocr.web.order.entity.data.Constant;
import org.millions.idea.ocr.web.order.entity.db.WalletEntity;
import org.millions.idea.ocr.web.order.entity.enums.transfer.Exceptions;
import org.millions.idea.ocr.web.order.entity.exception.FinanceException;
import org.millions.idea.ocr.web.order.repository.mapper.IMoneyChangeLogMapperRepository;
import org.millions.idea.ocr.web.order.repository.mapper.ITransactionRecordMapperRepository;
import org.millions.idea.ocr.web.order.repository.mapper.IWalletMapperRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Report message queue listener.
 */
@Service("ReportMessageQueueListener")
public class ReportMessageQueueListenerImpl extends BaseAddOperationImpl<PayParam, String> implements IPayMessageQueueListener {
    /**
     * The constant logger.
     */
    final static Logger logger = LogManager.getLogger(ReportMessageQueueListenerImpl.class);


    /**
     * Instantiates a new Report message queue listener.
     *
     * @param transactionRecordMapperRepository the transaction record mapper repository
     * @param moneyChangeLogMapperRepository    the money change log mapper repository
     * @param walletMapperRepository            the wallet mapper repository
     */
    protected ReportMessageQueueListenerImpl(ITransactionRecordMapperRepository transactionRecordMapperRepository, IMoneyChangeLogMapperRepository moneyChangeLogMapperRepository, IWalletMapperRepository walletMapperRepository) {
        super(transactionRecordMapperRepository, moneyChangeLogMapperRepository, walletMapperRepository);
    }



    @Override
    public void onMessage(Channel channel, Envelope envelope, String message) {
        logger.info("报错参数:" + message);

        try {
            // 转化JSON
            PayParam model = JsonUtil.getModel(message, PayParam.class);

            // 生成存储过程所需的参数列表
            Map map = createParamMap(model);

            // 支持并发重入的核心动作
            RetryAction retryAction = new RetryAction(model, map).invoke();
            Object result = retryAction.getResult();

            if(result == null) throw new FinanceException("交易失败", Exceptions.ORDER_BUY_ERROR);
            String status = String.valueOf(result);
            logger.info("报错结果:" + status);

            if(status.equalsIgnoreCase("SUCCESS")) {
                channel.basicAck(envelope.getDeliveryTag(), false);
                return;
            }

            throw new FinanceException(Exceptions.valueOf(status).getError().toString(), Exceptions.valueOf(status));
        } catch (IOException e) {
            logger.error("确认ACK消息时抛出异常" + e.toString());
            System.err.println("确认ACK消息时抛出异常");
        }
    }


    private Map createParamMap(PayParam model) {
        Map map = new HashMap();
        map.put("captchaId", model.getCaptchaId());
        map.put("fromUid",  Constant.getSharedAccountID());
        map.put("toUid",model.getUid());
        map.put("unitAmount", model.getUnitPrice());
        map.put("type",3);
        return map;
    }


    private class RetryAction {
        private PayParam model;
        private Map map;
        private Object result;
        private WalletEntity entity;

        /**
         * Instantiates a new Retry action.
         *
         * @param model the model
         * @param map   the map
         */
        public RetryAction(PayParam model, Map map) {
            this.model = model;
            this.map = map;
        }

        /**
         * Gets result.
         *
         * @return the result
         */
        public Object getResult() {
            return result;
        }

        /**
         * Gets entity.
         *
         * @return the entity
         */
        public WalletEntity getEntity() {
            return entity;
        }

        /**
         * Invoke retry action.
         *
         * @return the retry action
         */
        public RetryAction invoke() {
            // 高并发下失败重试机制
            result = null;
            long start =  System.currentTimeMillis();
            entity = null;
            while (true){
                // 获取循环当前时间
                long end = System.currentTimeMillis();
                // 当前时间已经超过最大间隔，返回失败
                if (end - start > 3000) {
                    throw new FinanceException("交易超时", Exceptions.ORDER_BUY_TIMEOUT);
                }
                transactionRecordMapperRepository.report(map);
                result = map.get("status");
                if(result != null) break;
            }
            return this;
        }
    }

}
