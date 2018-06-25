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
import org.millions.idea.ocr.web.common.utility.utils.PropertyUtil;
import org.millions.idea.ocr.web.user.biz.IWalletService;
import org.millions.idea.ocr.web.user.entity.db.Users;
import org.millions.idea.ocr.web.user.entity.db.Wallet;
import org.millions.idea.ocr.web.user.entity.ext.UserEntity;
import org.millions.idea.ocr.web.user.repository.mapper.IUserMapperRepository;
import org.millions.idea.ocr.web.user.repository.mapper.IWalletMapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class WalletServiceImpl implements IWalletService {
    private final IWalletMapperRepository walletMapperRepository;
    private final IUserMapperRepository userMapperRepository;
    private final RedisTemplate redisTemplate;

    @Autowired
    public WalletServiceImpl(IWalletMapperRepository walletMapperRepository, IUserMapperRepository userMapperRepository, RedisTemplate redisTemplate) {
        this.walletMapperRepository = walletMapperRepository;
        this.userMapperRepository = userMapperRepository;
        this.redisTemplate = redisTemplate;
    }

    /**
     * Reduce users wallet balance
     *
     * @param token
     * @param uid
     * @param channel
     * @return
     */
    @Override
    public boolean reduce(String token, Integer uid, String channel) {
        Wallet wallet = walletMapperRepository.select(uid);
        if (wallet.getBalance() <= 0) throw new MessageException("余额不足");
        boolean result =  walletMapperRepository.reduce(uid, channel, wallet.getVersion());
        if(!result) return false;

        wallet = walletMapperRepository.select(uid);
        Users user = userMapperRepository.query(uid);
        UserEntity userEntity = new UserEntity();
        PropertyUtil.clone(user, userEntity);
        userEntity.setWallet(wallet);
        userEntity.setToken(token);

        long expire = redisTemplate.getExpire(token).longValue();
        redisTemplate.opsForValue().set(token, JsonUtil.getJson(userEntity),expire, TimeUnit.SECONDS);
        return true;
    }

}
