/***
 * @pName mi-ocr-web-order
 * @name ReportMessageQueueLisenerImpl
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReportMessageQueueLisenerImpl extends BaseAddOperationImpl<PayParam, String> implements IPayMessageQueueListener {
    final static Logger logger = LogManager.getLogger(ReportMessageQueueLisenerImpl.class);


    protected ReportMessageQueueLisenerImpl(ITransactionRecordMapperRepository transactionRecordMapperRepository, IMoneyChangeLogMapperRepository moneyChangeLogMapperRepository, IWalletMapperRepository walletMapperRepository) {
        super(transactionRecordMapperRepository, moneyChangeLogMapperRepository, walletMapperRepository);
    }

    @Override
    public void onMessage(Channel channel, Envelope envelope, String message) {
        logger.info("扣费参数:" + message);

        /*
            报错规则
            1、更改交易流水状态
            2、系统资金公户调度资金给此用户
            3、将矫正消息发送到数据清洗消息队列中
        */
        try {
            // 转化JSON
            PayParam model = JsonUtil.getModel(message, PayParam.class);

            // 生成存储过程所需的参数列表
            Map map = createParamMap(model);

            // 支持并发重入的核心动作
            RetryAction retryAction = new ReportMessageQueueLisenerImpl.RetryAction(model, map).invoke();
            Object result = retryAction.getResult();

            if(result == null) throw new FinanceException("交易失败", Exceptions.ORDER_BUY_ERROR);
            String status = String.valueOf(result);
            logger.info("扣费结果:" + status);

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

        public RetryAction(PayParam model, Map map) {
            this.model = model;
            this.map = map;
        }

        public Object getResult() {
            return result;
        }

        public WalletEntity getEntity() {
            return entity;
        }

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
                transactionRecordMapperRepository.buy(map);
                result = map.get("status");
                if(result != null) break;
            }
            return this;
        }
    }

}
