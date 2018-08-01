/***
 * @pName mi-ocr-web-order
 * @name IOrderService
 * @user HongWei
 * @date 2018/7/31
 * @desc
 */
package org.millions.idea.ocr.web.order.biz.order;

import org.millions.idea.ocr.web.order.entity.agent.OrderDetailEntity;

import java.util.List;

public interface IOrderService {
    /**
     * 查询当天以及昨天的订单列表
     * @param uid
     * @return
     */
    List<OrderDetailEntity> getRecentOrders(Integer uid);
}
