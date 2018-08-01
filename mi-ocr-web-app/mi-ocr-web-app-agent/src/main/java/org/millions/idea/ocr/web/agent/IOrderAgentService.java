/***
 * @pName mi-ocr-web-app
 * @name IOrderAgentService
 * @user HongWei
 * @date 2018/7/31
 * @desc
 */
package org.millions.idea.ocr.web.agent;

import feign.Headers;
import org.millions.idea.ocr.web.entity.agent.OrderDetailEntity;
import org.millions.idea.ocr.web.entity.agent.WalletEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("${services.order}")
@Headers({"Content-Type: application/json","Accept: application/json"})
public interface IOrderAgentService {
    @RequestMapping(value = "/order/getRecentOrders?uid={uid}",method = RequestMethod.GET)
    List<OrderDetailEntity> getRecentOrders(@PathVariable("uid") Integer uid);
}
