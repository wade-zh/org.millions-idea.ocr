/***
 * @pName mi-ocr-web-order
 * @name TransferServiceImpl
 * @user HongWei
 * @date 2018/6/29
 * @desc
 */
package org.millions.idea.ocr.web.order.biz.impl;

import org.millions.idea.ocr.web.common.utility.date.DateUtil;
import org.millions.idea.ocr.web.common.utility.json.JsonUtil;
import org.millions.idea.ocr.web.order.biz.ITransferService;
import org.millions.idea.ocr.web.order.entity.data.Constant;
import org.millions.idea.ocr.web.order.entity.db.Transfers;
import org.millions.idea.ocr.web.order.entity.enums.transfer.Exceptions;
import org.millions.idea.ocr.web.order.entity.enums.transfer.Operations;
import org.millions.idea.ocr.web.order.entity.exception.FinanceException;
import org.millions.idea.ocr.web.order.repository.mapper.ITransferMapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public class TransferServiceImpl implements ITransferService {
    /**
     * 系统之间转账接口
     */
    private final ITransferMapperRepository transferRepository;

    @Autowired
    public TransferServiceImpl(ITransferMapperRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    /**
     * 系统扣费方法
     *
     * @param recvUid
     * @param amount
     * @return
     */
    @Override
    @Transactional
    public boolean transfer(Integer recvUid, Double amount) {
        /*
            事务详情

            1、从系统账户调拨资金流向用户账户，此时系统减去对应金额的钱
            2、用户接受由系统账户流转过来的资金，此时用户账户应增加对应金额的钱
            3、更新用户信息表中的余额信息
         */

        Transfers transfer = new Transfers();
        transfer.setTid(UUID.randomUUID().toString());
        transfer.setType(Operations.Reduce.getType());
        transfer.setSendUid(Constant.getSystemUser());
        transfer.setRecvUid(recvUid);
        transfer.setAmount(amount);
        transfer.setDesc("转账");
        transfer.setDate(DateUtil.convert(new Timestamp(System.currentTimeMillis())));
        Integer lineCount = 0;

        // 1、系统调拨资金流向用户
        lineCount = transferRepository.transfer(transfer);
        if(lineCount <= 0) throw new FinanceException(JsonUtil.getJson(transfer), Exceptions.SYSTEM_TRANSFER_ERROR);
        // 2、用户接受系统调拨资金
        transfer.setTid(UUID.randomUUID().toString());
        transfer.setType(Operations.Add.getType());
        transfer.setDesc("收款");
        transfer.setDate(DateUtil.convert(new Timestamp(System.currentTimeMillis())));
        lineCount = transferRepository.transfer(transfer);
        if(lineCount <= 0) throw new FinanceException(JsonUtil.getJson(transfer), Exceptions.SYSTEM_TRANSFER_ERROR);
        // 3、更新用户余额信息
        //throw new FinanceException("故意抛出的异常", Exceptions.USER_UPDATE_INFORMATION_ERROR);
        return true;
    }
}
