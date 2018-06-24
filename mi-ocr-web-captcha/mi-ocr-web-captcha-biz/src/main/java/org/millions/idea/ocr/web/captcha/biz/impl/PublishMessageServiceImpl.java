/***
 * @pName mi-ocr-web
 * @name PublishMessageServiceImpl
 * @user HongWei
 * @date 2018/6/18
 * @desc
 */
package org.millions.idea.ocr.web.captcha.biz.impl;

import org.millions.idea.ocr.web.captcha.agent.IUserAgentService;
import org.millions.idea.ocr.web.captcha.agent.IWalletAgentService;
import org.millions.idea.ocr.web.captcha.biz.util.EnumUtil;
import org.millions.idea.ocr.web.captcha.entity.MultiQueue;
import org.millions.idea.ocr.web.captcha.entity.UploadCaptchaReq;
import org.millions.idea.ocr.web.captcha.entity.common.LoginResult;
import org.millions.idea.ocr.web.captcha.entity.db.Users;
import org.millions.idea.ocr.web.captcha.utility.json.JsonUtil;
import org.millions.idea.ocr.web.captcha.utility.queue.RabbitUtil;
import org.millions.idea.ocr.web.common.entity.Captcha;
import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.millions.idea.ocr.web.common.entity.exception.MessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sun.plugin2.message.Message;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service("PublishMessageServiceImpl")
public class PublishMessageServiceImpl extends MessageServiceImpl {

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
        Object cacheData = redisTemplate.opsForValue().get(uploadCaptchaReq.getToken());
        if(cacheData == null) if(!extractUserInfo(uploadCaptchaReq)) throw new MessageException("登录异常");

        // 生成验证码票据并推送给后端系统
        UUID ticket = UUID.randomUUID();
        rabbitUtil.publish(multiQueue.getCaptcha(), JsonUtil.getJson(new Captcha(ticket.toString(), uploadCaptchaReq.getChannel(), uploadCaptchaReq.getBinary(), uploadCaptchaReq.getToken())));
        return ticket.toString();
    }

    private boolean extractUserInfo(UploadCaptchaReq uploadCaptchaReq){
        LoginResult loginResult = userAgentService.login(uploadCaptchaReq.getUname(), uploadCaptchaReq.getPwd());
        if(loginResult != null) {
            redisTemplate.delete(loginResult.getToken());
            redisTemplate.opsForValue().set(uploadCaptchaReq.getToken(), JsonUtil.getJson(loginResult.getUser()), 30, TimeUnit.MINUTES);
        }
        return false;
    }

}
