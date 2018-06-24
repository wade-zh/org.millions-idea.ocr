/***
 * @pName mi-ocr-web-captcha
 * @name IWalletMessageService
 * @user HongWei
 * @date 2018/6/24
 * @desc
 */
package org.millions.idea.ocr.web.captcha.biz;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;

public interface IWalletMessageService {
    void onMessage(Channel channel, Envelope envelope, String message);
}
