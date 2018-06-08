/***
 * @pName org.millions-idea.ocr
 * @name RabbitUtil
 * @user HongWei
 * @date 2018/6/8
 * @desc
 */
package org.millions.idea.ocr.utility.queue;

import org.millions.idea.ocr.entity.common.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitUtil {

    @Autowired
    private RabbitConfig rabbitConfig;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publish(String message){
        rabbitTemplate.convertAndSend(rabbitConfig.getQueue(), message);
    }
}
