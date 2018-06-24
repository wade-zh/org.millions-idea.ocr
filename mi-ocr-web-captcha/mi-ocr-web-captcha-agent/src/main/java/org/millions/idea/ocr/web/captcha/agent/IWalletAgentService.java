/***
 * @pName mi-ocr-web-captcha
 * @name IWalletAgentService
 * @user HongWei
 * @date 2018/6/24
 * @desc
 */
package org.millions.idea.ocr.web.captcha.agent;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mi-ocr-web-user-service")
@Headers("Content-Type:application/json")
public interface IWalletAgentService {
    @RequestLine("POST  /wallet/reduce")
    boolean reduce(@RequestParam("uid") Integer uid, @RequestParam("channel") String channel);
}
