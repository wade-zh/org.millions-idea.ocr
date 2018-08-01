/***
 * @pName mi-ocr-web-order
 * @name OrderController
 * @user HongWei
 * @date 2018/7/31
 * @desc
 */
package org.millions.idea.ocr.web.order.controller;

import org.millions.idea.ocr.web.order.biz.order.IOrderService;
import org.millions.idea.ocr.web.order.entity.agent.OrderDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @GetMapping("/getRecentOrders")
    public List<OrderDetailEntity> getRecentOrders(Integer uid){
        return orderService.getRecentOrders(uid);
    }
}
