/***
 * @pName mi-ocr-web-user
 * @name WalletServiceImpl
 * @user HongWei
 * @date 2018/6/23
 * @desc
 */
package org.millions.idea.ocr.web.user.biz.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    final static Logger logger = LogManager.getLogger(WalletServiceImpl.class);


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
     * @param channel
     * @return
     */
    @Override
    public boolean reduce(String token, String channel) {
        logger.info("扣减参数[token,channel]:" + token +"," +channel);
        Object cache = redisTemplate.opsForValue().get(token);
        if(cache==null) throw new MessageException("请重新登录");
        UserEntity userEntity = JsonUtil.getModel(String.valueOf(cache), UserEntity.class);
        logger.info("扣减参数[userEntity]:" + JsonUtil.getJson(userEntity));

        Wallet wallet = walletMapperRepository.select(userEntity.getUid());
        logger.info("扣减参数[wallet]:" + JsonUtil.getJson(userEntity));
        if (wallet.getBalance() <= 0) throw new MessageException(0, "余额不足");

        boolean result =  walletMapperRepository.reduce(userEntity.getUid(), channel, wallet.getVersion());
        if(!result) {  return false; }
        wallet = walletMapperRepository.select(userEntity.getUid());
        Users user = userMapperRepository.query(userEntity.getUid());
        PropertyUtil.clone(user, userEntity);
        userEntity.setWallet(wallet);
        userEntity.setToken(token);
        long expire = redisTemplate.getExpire(token).longValue();
        redisTemplate.opsForValue().set(token, JsonUtil.getJson(userEntity),expire, TimeUnit.SECONDS);
        return true;
    }


    /**
     * Reduce users wallet balance
     *
     * @param token
     * @return
     */
    @Override
    public boolean reduce(String token) {
        Object cache = redisTemplate.opsForValue().get(token);
        if(cache==null) throw new MessageException("请重新登录");
        UserEntity userEntity = JsonUtil.getModel(String.valueOf(cache), UserEntity.class);
        cache = redisTemplate.opsForValue().get("stock_" + userEntity.getUid());
        return walletMapperRepository.update(userEntity.getUid(), Integer.valueOf(String.valueOf(cache)));
    }


}
