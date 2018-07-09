/***
 * @pName mi-ocr-web-finance
 * @name PayServiceImpl
 * @user HongWei
 * @date 2018/6/30
 * @desc
 */
package org.millions.idea.ocr.web.order.biz.order.impl;

import org.millions.idea.ocr.web.common.utility.encrypt.Md5Util;
import org.millions.idea.ocr.web.common.utility.json.JsonUtil;
import org.millions.idea.ocr.web.order.biz.order.IPayService;
import org.millions.idea.ocr.web.order.entity.agent.PayParam;
import org.millions.idea.ocr.web.order.entity.data.Constant;
import org.millions.idea.ocr.web.order.entity.db.TransactionRecordEntity;
import org.millions.idea.ocr.web.order.entity.enums.transfer.Exceptions;
import org.millions.idea.ocr.web.order.entity.exception.FinanceException;
import org.millions.idea.ocr.web.order.repository.mapper.IMoneyChangeLogMapperRepository;
import org.millions.idea.ocr.web.order.repository.mapper.ITransactionRecordMapperRepository;
import org.millions.idea.ocr.web.order.repository.mapper.IWalletMapperRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class PayServiceImpl extends BaseAddOperationImpl<PayParam, String> implements IPayService {

    protected PayServiceImpl(ITransactionRecordMapperRepository transactionRecordMapperRepository, IMoneyChangeLogMapperRepository moneyChangeLogMapperRepository, IWalletMapperRepository walletMapperRepository) {
        super(transactionRecordMapperRepository, moneyChangeLogMapperRepository, walletMapperRepository);
    }

    /**
     * 兑换货币
     * @param model
     * @return
     * @throws FinanceException
     */
    @Override
    @Transactional
    public String exchange(PayParam model)  throws FinanceException {
        /*
            1、刷新用户余额
            2、生成交易单
            3、生成正负账单
         */
        logger.info("兑换货币参数:" + JsonUtil.getJson(model));

        model.setType(1);
        model.setFromUid(Constant.getSharedAccountID());
        model.setRemark("充值");
        model.setExceptions(Exceptions.ORDER_CREATE_TRANSACTION_RECORD);

        // 1、刷新用户余额
        reduceBalance(Constant.getSharedAccountID(), model.getUnitPrice());
        addBalance(model.getUid(), model.getUnitPrice());

        // 2、生成交易单,并返回交易单号
        String tradeNo = createTradeRecord(model);

        // 3、生成正负账单
        addMoneyMinusLog(tradeNo, Constant.getSharedAccountID(), model.getUnitPrice());
        addMoneyPlusLog(tradeNo, model.getUid(), model.getUnitPrice());
        return tradeNo;
    }

    /**
     * 消费
     *
     * @param model
     * @return
     */
    @Override
    @Transactional
    public String  buy(PayParam model) {
       /*
            1、刷新用户余额
            2、生成交易单
            3、生成正负账单
         */
        logger.info("消费参数:" + JsonUtil.getJson(model));

        model.setType(2);
        model.setFromUid(model.getUid());
        model.setRemark("转账");
        model.setExceptions(Exceptions.ORDER_CREATE_TRANSACTION_RECORD);

        // 1、刷新用户余额
        repetReduceBalance(model.getUid(), model.getUnitPrice(), 3);
        addBalance(Constant.getSharedAccountID(), model.getUnitPrice());

        // 2、生成交易单,并返回交易单号
        String tradeNo = createTradeRecord(model);

        // 3、生成正负账单
        addMoneyMinusLog(tradeNo, model.getUid(), model.getUnitPrice());
        addMoneyPlusLog(tradeNo, Constant.getSharedAccountID(), model.getUnitPrice());
        return tradeNo;
    }

    private void repetReduceBalance(Integer uid, BigDecimal unitAmount, Integer repetCount){
        for (int i = 0; i < repetCount; i++) {
            try {
                reduceBalance(uid, unitAmount);
                return;
            }catch (FinanceException ex){
                if(i == repetCount - 1) throw new FinanceException(ex.getMessage(), ex.getError());
                System.err.println(String.format("扣减余额失败，准备第%s次重试，异常信息：%s", i+1, ex.getError()));
            }
        }
    }

    /**
     * 调用存储过程实现消费操作
     *
     * @param model
     * @return
     */
    @Override
    public void buyCallable(PayParam model){
        Map map = new HashMap();
        map.put("captchaId", model.getCaptchaId());
        map.put("fromUid", model.getUid());
        map.put("toUid", Constant.getSharedAccountID());
        map.put("unitAmount", model.getUnitPrice());
        map.put("type",2);
        Object result = null;
        // 高并发下失败重试机制
        long start =  System.currentTimeMillis();
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
        if(result == null) throw new FinanceException("交易失败", Exceptions.ORDER_BUY_ERROR);
        String status = String.valueOf(result);
        if(status.equalsIgnoreCase("success")) return;
        throw new FinanceException(Exceptions.valueOf(status).getError().toString(), Exceptions.valueOf(status));
    }

    /**
     * 生成交易流水
     *
     * @param model
     * @return
     */
    @Override
    public String addTradeRecord(PayParam model) {
        model.setFromUid(model.getUid());
        model.setUid(Constant.getSharedAccountID());
        model.setType(2);
        model.setRemark("转账");
        model.setExceptions(Exceptions.ORDER_CREATE_TRANSACTION_RECORD);
        String recordId = createTradeRecord(model);
        String key = Md5Util.getMd5(model.getCaptchaId());
        redisTemplate.opsForValue().set(key,recordId, 3, TimeUnit.MINUTES);
        return recordId;
    }

    /**
     * 查询交易流水是否存在
     *
     * @param captchaId
     * @return
     */
    @Override
    public boolean isExitsTradeRecord(String captchaId) {
        TransactionRecordEntity entity = transactionRecordMapperRepository.selectByCaptchaId(captchaId);
        return entity != null;
    }

}
