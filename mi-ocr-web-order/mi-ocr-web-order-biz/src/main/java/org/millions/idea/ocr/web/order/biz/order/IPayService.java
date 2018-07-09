/***
 * @pName mi-ocr-web-finance
 * @name IPayService
 * @user HongWei
 * @date 2018/6/30
 * @desc
 */
package org.millions.idea.ocr.web.order.biz.order;

import org.millions.idea.ocr.web.order.entity.agent.PayParam;

public interface IPayService {
    /**
     * 兑换货币
     * @param model
     * @return
     */
    String exchange(PayParam model);

    /**
     * 消费
     * @param model
     * @return
     */
    String buy(PayParam model);

    /**
     * 调用存储过程实现消费操作
     * @param model
     * @return
     */
    void buyCallable(PayParam model);

    /**
     * 生成交易流水
     * @param model
     * @return
     */
    String addTradeRecord(PayParam model);

    /**
     * 查询交易流水是否存在
     * @param captchaId
     * @return
     */
    boolean isExitsTradeRecord(String captchaId);
}
