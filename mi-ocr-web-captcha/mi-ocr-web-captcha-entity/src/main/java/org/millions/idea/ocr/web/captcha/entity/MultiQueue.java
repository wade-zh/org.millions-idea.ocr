/***
 * @pName mi-ocr-web
 * @name MultiQueue
 * @user HongWei
 * @date 2018/6/19
 * @desc
 */
package org.millions.idea.ocr.web.captcha.entity;

import org.millions.idea.ocr.web.captcha.entity.common.RabbitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultiQueue {

    @Autowired
    private RabbitConfig rabbitConfig;

    private String captcha;
    private String report;

    public String getCaptcha() {
        return rabbitConfig.getQueue(0);
    }

    public String getReport() {
        return rabbitConfig.getQueue(1);
    }
}
