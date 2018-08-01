/***
 * @pName mi-ocr-web-order
 * @name BaseAddOperationImpl
 * @user HongWei
 * @date 2018/7/2
 * @desc
 */
package org.millions.idea.ocr.web.order.biz.order.impl;

import org.millions.idea.ocr.web.common.utility.date.DateUtil;
import org.millions.idea.ocr.web.order.entity.agent.PayParam;
import org.millions.idea.ocr.web.order.entity.data.Constant;
import org.millions.idea.ocr.web.order.entity.db.MoneyChangeLog;
import org.millions.idea.ocr.web.order.entity.db.TransactionRecord;
import org.millions.idea.ocr.web.order.entity.db.Wallet;
import org.millions.idea.ocr.web.order.entity.enums.transfer.Exceptions;
import org.millions.idea.ocr.web.order.entity.exception.FinanceException;
import org.millions.idea.ocr.web.order.repository.mapper.IMoneyChangeLogMapperRepository;
import org.millions.idea.ocr.web.order.repository.mapper.ITransactionRecordMapperRepository;
import org.millions.idea.ocr.web.order.repository.mapper.IWalletMapperRepository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public class BaseAddOperationImpl<T,R>  extends BaseOrderServiceImpl<T,R>  {
    protected BaseAddOperationImpl(ITransactionRecordMapperRepository transactionRecordMapperRepository, IMoneyChangeLogMapperRepository moneyChangeLogMapperRepository, IWalletMapperRepository walletMapperRepository) {
        super(transactionRecordMapperRepository, moneyChangeLogMapperRepository, walletMapperRepository);
    }

    /**
     * 新增交易流水
     * @param model
     * @return
     * @throws FinanceException
     */
    protected String createTradeRecord(PayParam model) throws FinanceException {
        TransactionRecord entity = new TransactionRecord();
        String recordId = UUID.randomUUID().toString().replace("-", "");
        entity.setRecordId(recordId);
        entity.setRecordNo(Constant.getSharedTradeNo());
        entity.setFromUid(model.getFromUid());
        entity.setToUid(model.getUid());
        entity.setTradeType(model.getType());
        entity.setTradeAmount(model.getUnitPrice());
        entity.setRemark(model.getRemark());
        entity.setTradeDate(DateUtil.convert(new Timestamp(System.currentTimeMillis())));
        entity.setCaptchaId(model.getCaptchaId());
        entity.setChannelId(model.getChannelId());
        int lineCount = transactionRecordMapperRepository.insert(entity);
        if(lineCount <= 0) throw new FinanceException(String.format("%s失败", model.getRemark()), model.getExceptions());
        return recordId;
    }

    /**
     * 扣减用户余额
     * @param uid
     * @param amount
     * @return
     */
    protected void reduceBalance(Integer uid, BigDecimal amount) throws FinanceException{
        Wallet entity = walletMapperRepository.selectByUid(uid);
        if(entity == null) throw new FinanceException("查询钱包失败",Exceptions.ORDER_SELECT_WALLET_INFO);
        if(entity.getBalance().compareTo(BigDecimal.ZERO) == 1 || entity.getBalance().compareTo(amount) == 1) throw new FinanceException("余额不足",Exceptions.ORDER_WALLET_BALANCE_ZERO);
        int lineCount = walletMapperRepository.reduceBalance(uid, amount, entity.getVersion());
        System.err.println(String.format("更新失败 uid:%s, amount:%s, version:%s", String.valueOf(uid), String.valueOf(amount), String.valueOf(entity.getVersion())));
        if(lineCount <= 0) {
            Wallet wallet = walletMapperRepository.selectByUid(uid);
            if (wallet == null) throw new FinanceException("查询钱包失败",Exceptions.ORDER_SELECT_WALLET_INFO);
            if(entity.getBalance().compareTo(BigDecimal.ZERO) == 1){
                throw new FinanceException("余额不足",Exceptions.ORDER_WALLET_BALANCE_ZERO);
            }else{
                throw new FinanceException("扣减失败",Exceptions.ORDER_REDUCE_WALLET_BALANCE);
            }
        }
    }

    /**
     * 增加用户余额
     * @param uid
     * @param amount
     * @return
     */
    protected void addBalance(Integer uid, BigDecimal amount) throws FinanceException{
        int lineCount = walletMapperRepository.addBalance(uid, amount);
        if(lineCount <= 0) throw new FinanceException("入账失败",Exceptions.ORDER_ADD_WALLET_BALANCE);
    }

    /**
     * 生成支出账单日志
     * @param recordId
     * @param fromUid
     * @param amount
     * @throws FinanceException
     */
    protected void addMoneyMinusLog(String recordId, Integer fromUid, BigDecimal amount) throws FinanceException{
        MoneyChangeLog entity = new MoneyChangeLog();
        String logId = UUID.randomUUID().toString().replace("-", "");
        entity.setLogId(logId);
        entity.setRecordId(recordId);
        entity.setFromUid(fromUid);
        entity.setTradeType(2);
        entity.setTradeAmount(amount);
        entity.setRemark("支出");
        entity.setAddDate(DateUtil.convert(new Timestamp(System.currentTimeMillis())));
        int lineCount = moneyChangeLogMapperRepository.insert(entity);
        if(lineCount <= 0) throw new FinanceException("生成支出账单失败", Exceptions.ORDER_CREATE_MONEY_CHANGE_MINUS_LOG);
    }

    /**
     * 生成入账账单日志
     * @param recordId
     * @param fromUid
     * @param amount
     * @throws FinanceException
     */
    protected void addMoneyPlusLog(String recordId, Integer fromUid, BigDecimal amount) throws FinanceException{
        MoneyChangeLog entity = new MoneyChangeLog();
        String logId = UUID.randomUUID().toString().replace("-", "");
        entity.setLogId(logId);
        entity.setRecordId(recordId);
        entity.setFromUid(fromUid);
        entity.setTradeType(1);
        entity.setTradeAmount(amount);
        entity.setRemark("入账");
        entity.setAddDate(DateUtil.convert(new Timestamp(System.currentTimeMillis())));
        int lineCount = moneyChangeLogMapperRepository.insert(entity);
        if(lineCount <= 0) throw new FinanceException("生成入账账单失败", Exceptions.ORDER_CREATE_MONEY_CHANGE_PLUS_LOG);
    }
}
