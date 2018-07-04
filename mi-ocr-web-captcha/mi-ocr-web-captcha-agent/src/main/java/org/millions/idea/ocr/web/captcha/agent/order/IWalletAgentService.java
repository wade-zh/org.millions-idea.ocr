/***
 * @pName mi-ocr-web-captcha
 * @name IWalletAgentService
 * @user HongWei
 * @date 2018/6/24
 * @desc
 */
package org.millions.idea.ocr.web.captcha.agent.order;

import feign.Headers;
import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("${services.user}")
@Headers("Content-Type: application/json")
public interface IWalletAgentService {

    @GetMapping("/getBalance")
    public HttpResp getBalance(String token);
}
