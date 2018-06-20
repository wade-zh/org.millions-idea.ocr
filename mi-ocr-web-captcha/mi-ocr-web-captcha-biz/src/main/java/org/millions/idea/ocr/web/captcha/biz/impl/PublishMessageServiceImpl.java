/***
 * @pName mi-ocr-web
 * @name PublishMessageServiceImpl
 * @user HongWei
 * @date 2018/6/18
 * @desc
 */
package org.millions.idea.ocr.web.captcha.biz.impl;

import org.millions.idea.ocr.common.entity.Captcha;
import org.millions.idea.ocr.web.captcha.biz.util.EnumUtil;
import org.millions.idea.ocr.web.captcha.entity.MultiQueue;
import org.millions.idea.ocr.web.captcha.utility.json.JsonUtil;
import org.millions.idea.ocr.web.captcha.utility.queue.RabbitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("PublishMessageServiceImpl")
public class PublishMessageServiceImpl extends MessageServiceImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MultiQueue multiQueue;

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
    public String publish(byte[] binary, String channel) {
        if(!EnumUtil.isExist(channel))
            return null;
        UUID ticket = UUID.randomUUID();
        rabbitUtil.publish(multiQueue.getCaptcha(), JsonUtil.getJson(new Captcha(ticket.toString(), channel, binary)));
        return ticket.toString();
    }



}
