/***
 * @pName mi-ocr-web
 * @name IMessageService
 * @user HongWei
 * @date 2018/6/18
 * @desc
 */
package org.millions.idea.ocr.web.biz;

public interface IMessageService {
    String publish(byte[] binary, String channel);

    String getCaptcha(String cid);
}
