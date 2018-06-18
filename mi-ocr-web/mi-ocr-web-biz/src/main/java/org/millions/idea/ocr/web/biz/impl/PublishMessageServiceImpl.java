/***
 * @pName mi-ocr-web
 * @name PublishMessageServiceImpl
 * @user HongWei
 * @date 2018/6/18
 * @desc
 */
package org.millions.idea.ocr.web.biz.impl;

import org.millions.idea.ocr.common.entity.Captcha;
import org.millions.idea.ocr.web.utility.json.JsonUtil;
import org.millions.idea.ocr.web.utility.queue.RabbitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("PublishMessageServiceImpl")
public class PublishMessageServiceImpl extends MessageServiceImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public PublishMessageServiceImpl(RabbitUtil rabbitUtil, RedisTemplate redisTemplate) {
        super(rabbitUtil, redisTemplate);
    }

    @Override
    public String publish(byte[] binary, String channel) {
        UUID ticket = UUID.randomUUID();
        rabbitUtil.publish(JsonUtil.getJson(new Captcha(ticket.toString(), channel, binary)));
        return ticket.toString();
    }

}
