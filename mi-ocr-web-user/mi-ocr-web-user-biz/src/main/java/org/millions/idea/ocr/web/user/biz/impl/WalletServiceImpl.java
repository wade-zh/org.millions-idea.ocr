/***
 * @pName mi-ocr-web-user
 * @name WalletServiceImpl
 * @user HongWei
 * @date 2018/6/23
 * @desc
 */
package org.millions.idea.ocr.web.user.biz.impl;

import org.millions.idea.ocr.web.common.entity.exception.MessageException;
import org.millions.idea.ocr.web.common.utility.json.JsonUtil;
import org.millions.idea.ocr.web.user.biz.IWalletService;
import org.millions.idea.ocr.web.user.entity.db.Users;
import org.millions.idea.ocr.web.user.entity.db.Wallet;
import org.millions.idea.ocr.web.user.repository.mapper.IWalletMapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sun.plugin2.message.Message;

@Service
public class WalletServiceImpl implements IWalletService {
    private final IWalletMapperRepository walletMapperRepository;
    private final RedisTemplate redisTemplate;

    @Autowired
    public WalletServiceImpl(IWalletMapperRepository walletMapperRepository, RedisTemplate redisTemplate) {
        this.walletMapperRepository = walletMapperRepository;
        this.redisTemplate = redisTemplate;
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
        Wallet wallet = walletMapperRepository.select(uid);
        if (wallet == null) throw new MessageException("用户不存在");
        if (wallet.getBalance() <= 0) throw new MessageException("余额不足");
        return walletMapperRepository.reduce(uid, channel, wallet.getVersion());
    }

}
