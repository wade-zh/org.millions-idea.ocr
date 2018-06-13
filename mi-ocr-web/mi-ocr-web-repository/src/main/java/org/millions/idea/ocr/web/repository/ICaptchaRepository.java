/***
 * @pName org.millions-idea.ocr
 * @name ICaptchaRepository
 * @user HongWei
 * @date 2018/6/8
 * @desc
 */
package org.millions.idea.ocr.web.repository;

import org.millions.idea.ocr.web.entity.types.CaptchaType;

public interface ICaptchaRepository {

    /**
     * Publish the captcha messages to the queue
     * @param ticket
     * @param captchaType
     * @param captchaEncode
     * @return ticket for uuid
     */
    public String insert(String ticket, CaptchaType captchaType, String captchaEncode);
}
