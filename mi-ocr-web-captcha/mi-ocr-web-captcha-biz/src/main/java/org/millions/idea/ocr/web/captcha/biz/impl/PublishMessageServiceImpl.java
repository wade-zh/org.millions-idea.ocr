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
import org.aspectj.apache.bcel.classfile.Constant;
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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service("PublishMessageServiceImpl")
public class PublishMessageServiceImpl extends MessageServiceImpl {
    final static Logger logger = LogManager.getLogger(PublishMessageServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MultiQueue multiQueue;

    @Autowired
    private IPayAgentClient payAgentClient;

    public PublishMessageServiceImpl(RabbitUtil rabbitUtil, RedisTemplate redisTemplate) {
        super(rabbitUtil, redisTemplate);
    }

    /**
     * publish to report error captcha queue
     *
     * @return
     */
    @Override
    public boolean publish(String channel, String token, String captchaId) {
        String userJson = SessionUtil.getUserInfo(redisTemplate, token);
        if(userJson == null) throw new MessageException("请重新登录");
        UserEntity userEntity = JsonUtil.getModel(userJson, UserEntity.class);

        BigDecimal unitAmount = getChannelAmount(channel);

        PayParam payParam = new PayParam();
        payParam.setUid(userEntity.getUid());
        payParam.setUnitPrice(unitAmount);
        payParam.setCaptchaId(captchaId);
        logger.info("报错参数:" + JsonUtil.getJson(payParam));

        rabbitUtil.publish(multiQueue.getReport(), JsonUtil.getJson(payParam));
        return true;
    }


    @Override
    public String publish(UploadCaptchaReq model) {
        if(!EnumUtil.isExist(model.getChannel())) throw new MessageException("类型不存在");

        BigDecimal unitAmount = getChannelAmount(model.getChannel());

        validate(model, unitAmount);

        String userJson = SessionUtil.getUserInfo(redisTemplate, model.getToken());
        if(userJson == null) throw new MessageException("请重新登录");
        UserEntity userEntity = JsonUtil.getModel(userJson, UserEntity.class);

        // 验证码票据
        String captchaId = UUID.randomUUID().toString().replace("-","");

        // 生成交易流水
        PayParam payParam = new PayParam();
        payParam.setUid(userEntity.getUid());
        payParam.setUnitPrice(unitAmount);
        payParam.setCaptchaId(captchaId.toString());

        logger.info("扣费参数:" + JsonUtil.getJson(payParam));
        HttpResp resp = payAgentClient.addTradeRecord(payParam);
        logger.info("扣费结果:" + JsonUtil.getJson(resp));
        if(resp.getError() != 0) throw new MessageException(resp.getMsg());
        if (resp.getMsg().length() < 32) throw new MessageException("生成交易单据失败");
        payParam.setRecordId(resp.getMsg());

        // 发布到延迟处理消息队列并返回消费id
        rabbitUtil.publish(multiQueue.getOrderPay(), JsonUtil.getJson(payParam));
        rabbitUtil.publish(multiQueue.getCaptcha(), JsonUtil.getJson(new Captcha(captchaId, model.getChannel(), model.getBinary(), model.getToken())));
        return captchaId;

    }


    /**
     * 通过channel代码取出对应的单价
     * @param channel
     * @return
     */
    private BigDecimal getChannelAmount(String channel){
        Object cache = redisTemplate.opsForValue().get(channel);
        if(cache == null) throw new MessageException("频道数据异常，请联系管理员维护");
        return BigDecimal.valueOf(Double.valueOf(String.valueOf(cache)));
    }


    private BigDecimal validate(UploadCaptchaReq uploadCaptchaReq, BigDecimal unitAmount) {
        // 判断token是否过期，不获取过期的内容
        Object cache = redisTemplate.opsForValue().get(uploadCaptchaReq.getToken());
        if(cache == null) throw new MessageException("请重新登录");
        // 判断通过token取出的数据是否为标准格式
        UserEntity userEntity = JsonUtil.getModel(String.valueOf(cache), UserEntity.class);
        cache = redisTemplate.opsForValue().get("stock_" + userEntity.getUid());
        if(cache == null) throw new MessageException("请重新登录");
        // 判断余额不足
        BigDecimal balance = new BigDecimal(String.valueOf(cache));
        if(balance.compareTo(unitAmount) != 1) throw new MessageException("余额不足");
        // 判断通道
        cache = redisTemplate.opsForValue().get(uploadCaptchaReq.getChannel());
        if(cache == null) throw new MessageException("类型不存在");
        // 扣减库存
        BigDecimal baseReduceNum = new BigDecimal(String.valueOf(cache));
        BigDecimal reduceCode = reduce("stock_" + userEntity.getUid(), baseReduceNum);
        if (reduceCode.compareTo(BigDecimal.ZERO) == -1 || reduceCode.compareTo(BigDecimal.ZERO) == 0) throw new MessageException("扣减库存失败");
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

    public  BigDecimal reduce(String key, BigDecimal num) {
        Object data = redisTemplate.opsForValue().get(key);
        if(data == null) throw new MessageException("请重新登录");
        DefaultRedisScript<BigDecimal> script = new DefaultRedisScript<BigDecimal>();
        script.setScriptText(REDUCE_SCRIPT);
        script.setResultType(BigDecimal.class);
        Object result = redisTemplate.execute(script,
                Collections.singletonList(key), num.toString());
        BigDecimal code = new BigDecimal(String.valueOf(result));
        Long expire = redisTemplate.getExpire(key).longValue();
        if(expire <= 0L) expire = 1L;
        if(code.compareTo(BigDecimal.ZERO) == -1 || code.compareTo(BigDecimal.ZERO) == 0) redisTemplate.expire(key, expire, TimeUnit.DAYS);
        return code;
    }

}
