/***
 * @pName mi-ocr-web
 * @name IMessageService
 * @user HongWei
 * @date 2018/6/18
 * @desc
 */
package org.millions.idea.ocr.web.captcha.biz;

public interface IMessageService {
    /**
     * publish to captcha leaning queue
     * @param binary
     * @param channel
     * @return
     */
    String publish(byte[] binary, String channel);

    /**
     * publish to report error captcha queue
     * @param cid
     * @return
     */
    void publish(String cid);

    String getCaptcha(String cid);
}
