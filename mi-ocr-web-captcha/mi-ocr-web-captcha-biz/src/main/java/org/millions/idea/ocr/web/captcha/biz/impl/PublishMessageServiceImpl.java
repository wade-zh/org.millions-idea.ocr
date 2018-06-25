/***
 * @pName mi-ocr-web
 * @name PublishMessageServiceImpl
 * @user HongWei
 * @date 2018/6/18
 * @desc
 */
package org.millions.idea.ocr.web.captcha.biz.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.millions.idea.ocr.web.captcha.agent.IUserAgentService;
import org.millions.idea.ocr.web.captcha.biz.util.EnumUtil;
import org.millions.idea.ocr.web.captcha.entity.MultiQueue;
import org.millions.idea.ocr.web.captcha.entity.UploadCaptchaReq;
import org.millions.idea.ocr.web.captcha.entity.ext.UserEntity;
import org.millions.idea.ocr.web.captcha.utility.json.JsonUtil;
import org.millions.idea.ocr.web.captcha.utility.queue.RabbitUtil;
import org.millions.idea.ocr.web.common.entity.Captcha;
import org.millions.idea.ocr.web.common.entity.exception.MessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service("PublishMessageServiceImpl")
public class PublishMessageServiceImpl extends MessageServiceImpl {
    final static Logger logger = LogManager.getLogger(PublishMessageServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MultiQueue multiQueue;

    @Autowired
    private IUserAgentService userAgentService;


    public PublishMessageServiceImpl(RabbitUtil rabbitUtil, RedisTemplate redisTemplate) {
        super(rabbitUtil, redisTemplate);
    }

    /**
     * publish to report error captcha queue
     *
     * @param cid
     * @return
     */
    @Override
    @Async
    public void publish(String cid) {
        rabbitUtil.publish(multiQueue.getReport(), cid);
    }


    @Override
    public String publish(UploadCaptchaReq uploadCaptchaReq) {
        if(!EnumUtil.isExist(uploadCaptchaReq.getChannel()))
            return null;

        // 查询用户信息
        Object cache = redisTemplate.opsForValue().get(uploadCaptchaReq.getToken());
        if(cache == null) throw new MessageException("请重新登录");
        UserEntity userEntity = JsonUtil.getModel(String.valueOf(cache), UserEntity.class);
        cache = redisTemplate.opsForValue().get("stock_" + userEntity.getUid());
        if(cache == null) throw new MessageException("请重新登录");
        if(Integer.valueOf(String.valueOf(cache)) <= 0) throw new MessageException("余额不足");
        cache = redisTemplate.opsForValue().get(uploadCaptchaReq.getChannel());
        if(cache == null) throw new MessageException("类型不存在");
        Integer baseReduceNum = Integer.valueOf(String.valueOf(cache));
        reduce("stock_" + userEntity.getUid(), baseReduceNum);

        // 生成验证码票据并推送给后端系统
        UUID ticket = UUID.randomUUID();
        rabbitUtil.publish(multiQueue.getCaptcha(), JsonUtil.getJson(new Captcha(ticket.toString(), uploadCaptchaReq.getChannel(), uploadCaptchaReq.getBinary(), uploadCaptchaReq.getToken())));
        return ticket.toString();
    }

    private static final String REDUCE_SCRIPT;

    static {
        StringBuilder  reduceScript = new StringBuilder();
        reduceScript.append("local userKey = KEYS[1] ");
        reduceScript.append("local num = tonumber(ARGV[1]) ");
        reduceScript.append("if redis.call(\"exists\", userKey) == 1 then ");
        reduceScript.append("local stock = tonumber(redis.call(\"get\", userKey)) ");
        reduceScript.append("if stock <= 0 then return -1001 end ");
        reduceScript.append("if stock >= num then return redis.call('incrbyfloat', userKey, 0 - num) end  ");
        reduceScript.append("else ");
        reduceScript.append("return -1000 ");
        reduceScript.append("end ");

        REDUCE_SCRIPT = reduceScript.toString();
    }

    public Integer reduce(String key, Integer num) {
        DefaultRedisScript<Integer> script = new DefaultRedisScript<Integer>();
        script.setScriptText(REDUCE_SCRIPT);
        script.setResultType(Integer.class);
        Object result = redisTemplate.execute(script,
                Collections.singletonList(key), num.toString());
        Integer code = Integer.valueOf(String.valueOf(result));
        if(code > 0) redisTemplate.expire(key, redisTemplate.getExpire(key).intValue(), TimeUnit.DAYS);
        return code;
    }

}
