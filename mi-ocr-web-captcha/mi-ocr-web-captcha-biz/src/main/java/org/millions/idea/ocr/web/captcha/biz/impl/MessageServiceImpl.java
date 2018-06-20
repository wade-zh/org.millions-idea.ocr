/***
 * @pName mi-ocr-web
 * @name MessageServiceImpl
 * @user HongWei
 * @date 2018/6/18
 * @desc
 */
package org.millions.idea.ocr.web.captcha.biz.impl;

import org.millions.idea.ocr.web.captcha.biz.IMessageService;
import org.millions.idea.ocr.web.captcha.utility.queue.RabbitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service(value = "MessageServiceImpl")
public class MessageServiceImpl implements IMessageService {
    protected final RabbitUtil rabbitUtil;
    protected final RedisTemplate redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    public MessageServiceImpl(RabbitUtil rabbitUtil, RedisTemplate redisTemplate) {
        this.rabbitUtil = rabbitUtil;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public String publish(byte[] binary, String channel) {
        return null;
    }

    /**
     * publish to report error captcha queue
     *
     * @param cid
     * @return
     */
    @Override
    @Async
    public void publish(String cid) {}

    @Override
    public String getCaptcha(String cid) {
        /**
         * 验证码收取规则:
         *  当后端系统将识别结果发送到缓存服务器时，用户主动调用本方法从缓存服务器中取出验证码
         *  当用户成功取出验证码后，将缓存删除，并且，将识别结果上传到样本数据分析中心
         */

        Object captcha = redisTemplate.opsForValue().get(cid);
        if(captcha != null && !captcha.toString().equalsIgnoreCase("null")) {
            String code = captcha.toString();
            redisTemplate.delete(code.toString());
            /*Query query = new Query();
            query.addCriteria(Criteria.where("captchaId").is(cid));
            Update update = Update.update("code", code);
            mongoTemplate.updateFirst(query, update, "samples");*/
            return code;
        }
        return null;
    }


}
