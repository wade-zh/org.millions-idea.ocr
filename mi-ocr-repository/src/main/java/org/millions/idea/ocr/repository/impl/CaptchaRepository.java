/***
 * @pName org.millions-idea.ocr
 * @name CaptchaRepository
 * @user HongWei
 * @date 2018/6/8
 * @desc
 */
package org.millions.idea.ocr.repository.impl;

import org.millions.idea.ocr.entity.types.CaptchaType;
import org.millions.idea.ocr.repository.ICaptchaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CaptchaRepository implements ICaptchaRepository {


    /**
     * Publish the captcha messages to the queue
     *
     * @param ticket
     * @param captchaType
     * @param captchaEncode
     * @return ticket for uuid
     */
    @Override
    public String insert(String ticket, CaptchaType captchaType, String captchaEncode) {
        /*return rabbitUtil.pub(new RabbitConfig(defaultQueueName, defaultExchange, defaultRoutingKey)
                , ticket, JsonUtil.getJson(new CaptchaReq(ticket, captchaType, captchaEncode)));*/
        return "";
    }
}
