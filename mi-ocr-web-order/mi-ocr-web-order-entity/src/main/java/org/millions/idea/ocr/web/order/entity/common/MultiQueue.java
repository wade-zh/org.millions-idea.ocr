/***
 * @pName mi-ocr-web
 * @name MultiQueue
 * @user HongWei
 * @date 2018/6/19
 * @desc
 */
package org.millions.idea.ocr.web.order.entity.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultiQueue {

    @Autowired
    private RabbitConfig rabbitConfig;

    private String orderPay;
    private String report;

    public String getOrderPay() {
        return rabbitConfig.getQueue(0);
    }

    public String getReport() {
        return rabbitConfig.getQueue(1);
    }
}
