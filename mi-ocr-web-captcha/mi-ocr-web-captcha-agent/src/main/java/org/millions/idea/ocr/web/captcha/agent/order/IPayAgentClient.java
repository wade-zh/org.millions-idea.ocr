/***
 * @pName mi-ocr-web-captcha
 * @name IPayAgentClient
 * @user HongWei
 * @date 2018/7/2
 * @desc
 */
package org.millions.idea.ocr.web.captcha.agent.order;

import feign.Headers;
import org.millions.idea.ocr.web.captcha.entity.agent.order.PayParam;
import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("${services.order}")
@Headers({"Content-Type: application/json","Accept: application/json"})
public interface IPayAgentClient {
    /**
     * 消费接口
     * @param payParam
     * @return
     */
    @PostMapping(value = "/pay/buy", consumes = "application/json")
    HttpResp buy(PayParam payParam);
}
