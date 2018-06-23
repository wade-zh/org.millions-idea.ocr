/***
 * @pName mi-ocr-web-user
 * @name WalletServiceImpl
 * @user HongWei
 * @date 2018/6/23
 * @desc
 */
package org.millions.idea.ocr.web.user.biz.impl;

import org.millions.idea.ocr.web.user.biz.IWalletService;
import org.millions.idea.ocr.web.user.repository.mapper.IWalletMapperRepository;
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
     * Reduce users wallet balance
     *
     * @param uid
     * @param channel
     * @return
     */
    @Override
    public boolean reduce(Integer uid, String channel) {
        return walletMapperRepository.Reduce(uid, channel);
    }
}
