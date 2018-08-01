/***
 * @pName mi-ocr-web-app
 * @name OrderApiController
 * @user HongWei
 * @date 2018/7/31
 * @desc
 */
package org.millions.idea.ocr.web.controller.api;

import com.fasterxml.jackson.annotation.JsonView;
import org.millions.idea.ocr.web.agent.IOrderAgentService;
import org.millions.idea.ocr.web.entity.agent.OrderDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order-api")
public class OrderApiController {
    @Autowired
    private IOrderAgentService orderAgentService;

    @GetMapping("/getRecentOrders")
    @JsonView(OrderDetailEntity.OrderSampleViewEntity.class)
    public List<OrderDetailEntity> getRecentOrders(Integer uid){
        return orderAgentService.getRecentOrders(uid);
    }
}
