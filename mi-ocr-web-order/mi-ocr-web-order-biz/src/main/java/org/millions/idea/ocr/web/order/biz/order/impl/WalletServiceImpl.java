/***
 * @pName mi-ocr-web-order
 * @name WalletServiceImpl
 * @user HongWei
 * @date 2018/7/4
 * @desc
 */
package org.millions.idea.ocr.web.order.biz.order.impl;

import org.millions.idea.ocr.web.common.utility.date.DateUtil;
import org.millions.idea.ocr.web.order.biz.order.IWalletService;
import org.millions.idea.ocr.web.order.entity.db.Wallet;
import org.millions.idea.ocr.web.order.repository.mapper.IWalletMapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class WalletServiceImpl implements IWalletService {
    private final IWalletMapperRepository walletMapperRepository;

    @Autowired
    public WalletServiceImpl(IWalletMapperRepository walletMapperRepository) {
        this.walletMapperRepository = walletMapperRepository;
    }

    /**
     * 查询wallet信息
     *
     * @param uid
     * @return
     */
    @Override
    public Wallet getWallet(Integer uid) {
        return walletMapperRepository.selectByUid(uid);
    }

    /**
     * 添加新钱包并返回主键 韦德 2018年8月1日10:13:25
     *
     * @param uid
     */
    @Override
    public Integer addNewWallet(Integer uid) {
        Wallet wallet = new Wallet();
        wallet.setUid(uid);
        wallet.setBalance(BigDecimal.valueOf(0.0000));
        wallet.setState(0);
        int count = walletMapperRepository.insert(wallet);
        if(count > 0 && wallet.getAutoId() != null) return wallet.getAutoId();
        return 0;
    }
}
