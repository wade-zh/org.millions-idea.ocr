package org.millions.idea.ocr.worker.app.service.impl;

import com.rabbitmq.client.Channel;
import org.millions.idea.ocr.common.entity.CaptchaEntity;
import org.millions.idea.ocr.common.utility.json.JsonUtil;
import org.millions.idea.ocr.worker.app.service.IMessageListenerService;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageListenServiceImpl implements IMessageListenerService {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String body = new String(message.getBody());

        CaptchaEntity captchaEntity = JsonUtil.getModel(body, CaptchaEntity.class);

        System.out.println(captchaEntity);

        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
