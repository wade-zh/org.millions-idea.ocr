/***
 * @pName mi-ocr-web-order
 * @name BaseOrderServiceImpl
 * @user HongWei
 * @date 2018/6/30
 * @desc
 */
package org.millions.idea.ocr.web.order.biz.order.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.millions.idea.ocr.web.order.biz.base.IOperationService;
import org.millions.idea.ocr.web.order.repository.mapper.IMoneyChangeLogMapperRepository;
import org.millions.idea.ocr.web.order.repository.mapper.ITransactionRecordMapperRepository;
import org.millions.idea.ocr.web.order.repository.mapper.IWalletMapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public abstract class BaseOrderServiceImpl<T,R> implements IOperationService<T,R> {
    public final static Logger logger = LogManager.getLogger(BaseOrderServiceImpl.class);
    protected final ITransactionRecordMapperRepository transactionRecordMapperRepository;
    protected final IMoneyChangeLogMapperRepository moneyChangeLogMapperRepository;
    protected final IWalletMapperRepository walletMapperRepository;

    @Autowired
    protected RedisTemplate redisTemplate;

    protected BaseOrderServiceImpl(ITransactionRecordMapperRepository transactionRecordMapperRepository, IMoneyChangeLogMapperRepository moneyChangeLogMapperRepository, IWalletMapperRepository walletMapperRepository) {
        this.transactionRecordMapperRepository = transactionRecordMapperRepository;
        this.moneyChangeLogMapperRepository = moneyChangeLogMapperRepository;
        this.walletMapperRepository = walletMapperRepository;
    }

    @Override
    public R add(T model) {
        return null;
    }

    @Override
    public boolean delete(T model) {
        return false;
    }

    @Override
    public boolean update(T model) {
        return false;
    }


}
