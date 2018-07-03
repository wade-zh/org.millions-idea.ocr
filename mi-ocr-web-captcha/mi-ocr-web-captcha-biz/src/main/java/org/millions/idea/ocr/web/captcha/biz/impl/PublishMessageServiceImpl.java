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
import org.millions.idea.ocr.web.captcha.agent.order.IPayAgentClient;
import org.millions.idea.ocr.web.captcha.agent.user.IUserAgentService;
import org.millions.idea.ocr.web.captcha.biz.util.EnumUtil;
import org.millions.idea.ocr.web.captcha.entity.MultiQueue;
import org.millions.idea.ocr.web.captcha.entity.UploadCaptchaReq;
import org.millions.idea.ocr.web.captcha.entity.agent.order.PayParam;
import org.millions.idea.ocr.web.captcha.entity.ext.UserEntity;
import org.millions.idea.ocr.web.captcha.utility.queue.RabbitUtil;
import org.millions.idea.ocr.web.common.entity.Captcha;
import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.millions.idea.ocr.web.common.entity.exception.MessageException;
import org.millions.idea.ocr.web.common.utility.json.JsonUtil;
import org.millions.idea.ocr.web.common.utility.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    @Autowired
    private IPayAgentClient payAgentClient;

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
    public String publish(UploadCaptchaReq model) {
        if(!EnumUtil.isExist(model.getChannel())) throw new MessageException("类型不存在");

        logger.info("发布消息参数:" + JsonUtil.getJson(model));

        String userJson = SessionUtil.getUserInfo(redisTemplate, model.getToken());
        if(userJson == null) throw new MessageException("请重新登录");
        UserEntity userEntity = JsonUtil.getModel(userJson, UserEntity.class);

        // 扣费
        PayParam payParam = new PayParam();
        payParam.setUid(userEntity.getUid());
        payParam.setUnitPrice(getChannelAmount(model.getChannel()));
        logger.info("扣费参数:" + JsonUtil.getJson(payParam));

        HttpResp resp = payAgentClient.buy(payParam);
        logger.info("扣费结果:" + JsonUtil.getJson(resp));

        if(resp.getError() != 0) throw new MessageException(resp.getMsg());

        // 发布到延迟处理消息队列并返回消费id
        UUID ticket = UUID.randomUUID();
        rabbitUtil.publish(multiQueue.getCaptcha(), JsonUtil.getJson(new Captcha(ticket.toString(), model.getChannel(), model.getBinary(), model.getToken())));
        return ticket.toString();

    }


    /**
     * 通过channel代码取出对应的单价
     * @param channel
     * @return
     */
    private Double getChannelAmount(String channel){
        Object cache = redisTemplate.opsForValue().get(channel);
        if(cache == null) throw new MessageException("频道数据异常，请联系管理员维护");
        return Double.valueOf(String.valueOf(cache));
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
