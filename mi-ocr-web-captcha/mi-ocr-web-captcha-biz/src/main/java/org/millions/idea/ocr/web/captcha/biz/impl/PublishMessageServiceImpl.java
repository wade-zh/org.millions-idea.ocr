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
import org.millions.idea.ocr.web.captcha.utility.queue.RabbitUtil;
import org.millions.idea.ocr.web.common.entity.Captcha;
import org.millions.idea.ocr.web.common.entity.exception.MessageException;
import org.millions.idea.ocr.web.common.utility.json.JsonUtil;
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
        logger.info("创建订单参数:" + JsonUtil.getJson(uploadCaptchaReq));

        if(!EnumUtil.isExist(uploadCaptchaReq.getChannel())) throw new MessageException("类型不存在");

        // 验证订单
        if(validate(uploadCaptchaReq) > 0){
            // 产出数据
            UUID ticket = UUID.randomUUID();
            rabbitUtil.publish(multiQueue.getCaptcha(), JsonUtil.getJson(new Captcha(ticket.toString(), uploadCaptchaReq.getChannel(), uploadCaptchaReq.getBinary(), uploadCaptchaReq.getToken())));
            return ticket.toString();
        }
        return null;
    }

    private Integer validate(UploadCaptchaReq uploadCaptchaReq) {
        // 判断token是否过期，不获取过期的内容
        Object cache = redisTemplate.opsForValue().get(uploadCaptchaReq.getToken());
        if(cache == null) throw new MessageException("请重新登录");
        // 判断通过token取出的数据是否为标准格式
        UserEntity userEntity = JsonUtil.getModel(String.valueOf(cache), UserEntity.class);
        cache = redisTemplate.opsForValue().get("stock_" + userEntity.getUid());
        if(cache == null) throw new MessageException("请重新登录");
        // 判断余额不足
        if(Integer.valueOf(String.valueOf(cache)) <= 0) throw new MessageException("余额不足");
        // 判断通道
        cache = redisTemplate.opsForValue().get(uploadCaptchaReq.getChannel());
        if(cache == null) throw new MessageException("类型不存在");
        // 扣减库存
        Integer baseReduceNum = Integer.valueOf(String.valueOf(cache));
        Integer reduceCode = reduce("stock_" + userEntity.getUid(), baseReduceNum);
        if (reduceCode < 0) throw new MessageException(reduceCode,"扣减库存失败");
        return reduceCode;
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
        Object data = redisTemplate.opsForValue().get(key);
        if(data == null) throw new MessageException("请重新登录");
        DefaultRedisScript<Integer> script = new DefaultRedisScript<Integer>();
        script.setScriptText(REDUCE_SCRIPT);
        script.setResultType(Integer.class);
        Object result = redisTemplate.execute(script,
                Collections.singletonList(key), num.toString());
        Integer code = Integer.valueOf(String.valueOf(result));
        Long expire = redisTemplate.getExpire(key).longValue();
        if(expire <= 0L) expire = 7*86400000L;
        if(code > 0) redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        return code;
    }

}
