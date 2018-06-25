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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        if(userEntity.getWallet().getBalance() <= 0) throw new MessageException("余额不足");

        // 生成验证码票据并推送给后端系统
        UUID ticket = UUID.randomUUID();
        rabbitUtil.publish(multiQueue.getCaptcha(), JsonUtil.getJson(new Captcha(ticket.toString(), uploadCaptchaReq.getChannel(), uploadCaptchaReq.getBinary(), uploadCaptchaReq.getToken())));
        return ticket.toString();
    }


}
