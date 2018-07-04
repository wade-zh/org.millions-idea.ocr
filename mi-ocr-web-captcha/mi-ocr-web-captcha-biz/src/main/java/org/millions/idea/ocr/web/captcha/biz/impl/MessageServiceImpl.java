/***
 * @pName mi-ocr-web
 * @name MessageServiceImpl
 * @user HongWei
 * @date 2018/6/18
 * @desc
 */
package org.millions.idea.ocr.web.captcha.biz.impl;

import org.millions.idea.ocr.web.captcha.agent.order.IPayAgentClient;
import org.millions.idea.ocr.web.captcha.biz.IMessageService;
import org.millions.idea.ocr.web.captcha.biz.util.EnumUtil;
import org.millions.idea.ocr.web.captcha.entity.UploadCaptchaReq;
import org.millions.idea.ocr.web.captcha.entity.common.SharedResult;
import org.millions.idea.ocr.web.captcha.utility.queue.RabbitUtil;
import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.millions.idea.ocr.web.common.entity.exception.MessageException;
import org.millions.idea.ocr.web.common.utility.encrypt.Md5Util;
import org.millions.idea.ocr.web.common.utility.json.JsonUtil;
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
    private IPayAgentClient payAgentClient;

    @Autowired
    public MessageServiceImpl(RabbitUtil rabbitUtil, RedisTemplate redisTemplate) {
        this.rabbitUtil = rabbitUtil;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public String publish(UploadCaptchaReq uploadCaptchaReq) {
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
        Object tradeRecord = redisTemplate.opsForValue().get(Md5Util.getMd5(cid));
        if(tradeRecord == null) {
            HttpResp resp = payAgentClient.isExitsTradeRecord(cid);
            if(resp == null || resp.getError() == 1) throw new MessageException("非法来源订单，请联系管理员维护");
        }
        Object captcha = redisTemplate.opsForValue().get(cid);
        if(captcha != null && !captcha.toString().equalsIgnoreCase("null") && captcha.toString().length() < 12) {
            String code = captcha.toString();
            if (code.contains("Ticket") && code.contains("Id") && code.contains("Result")){
                SharedResult model = JsonUtil.getModel(code, SharedResult.class);
                if(model == null) return null;
                code = model.getResult();
            }
            redisTemplate.delete(cid);
            return code;
        }
        return null;
    }


}
