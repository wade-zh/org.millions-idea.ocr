/***
 * @pName mi-ocr-web-order
 * @name WalletServiceImpl
 * @user HongWei
 * @date 2018/7/4
 * @desc
 */
package org.millions.idea.ocr.web.order.biz.order.impl;

import org.millions.idea.ocr.web.order.biz.order.IWalletService;
import org.millions.idea.ocr.web.order.entity.db.WalletEntity;
import org.millions.idea.ocr.web.order.repository.mapper.IWalletMapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public WalletEntity getWallet(Integer uid) {
        return walletMapperRepository.selectByUid(uid);
    }
}
