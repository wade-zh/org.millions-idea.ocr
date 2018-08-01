/***
 * @pName mi-ocr-web-order
 * @name OrderServiceImpl
 * @user HongWei
 * @date 2018/7/31
 * @desc
 */
package org.millions.idea.ocr.web.order.biz.order.impl;

import org.millions.idea.ocr.web.order.biz.order.IOrderService;
import org.millions.idea.ocr.web.order.entity.agent.OrderDetailEntity;
import org.millions.idea.ocr.web.order.repository.mapper.IMoneyChangeLogMapperRepository;
import org.millions.idea.ocr.web.order.repository.mapper.ITransactionRecordMapperRepository;
import org.millions.idea.ocr.web.order.repository.mapper.IWalletMapperRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends BaseOrderServiceImpl implements IOrderService {
    protected OrderServiceImpl(ITransactionRecordMapperRepository transactionRecordMapperRepository, IMoneyChangeLogMapperRepository moneyChangeLogMapperRepository, IWalletMapperRepository walletMapperRepository) {
        super(transactionRecordMapperRepository, moneyChangeLogMapperRepository, walletMapperRepository);
    }

    /**
     * 查询当天以及昨天的订单列表
     *
     * @param uid
     * @return
     */
    @Override
    public List<OrderDetailEntity> getRecentOrders(Integer uid) {
        return transactionRecordMapperRepository.selectRecentOrders(uid);
    }
}
