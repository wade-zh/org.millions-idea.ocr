/***
 * @pName mi-ocr-web-order
 * @name IPayMessageQueueListener
 * @user HongWei
 * @date 2018/7/4
 * @desc
 */
package org.millions.idea.ocr.web.order.biz.listener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;

public interface IPayMessageQueueListener {
    void onMessage(Channel channel, Envelope envelope, String message);
}
