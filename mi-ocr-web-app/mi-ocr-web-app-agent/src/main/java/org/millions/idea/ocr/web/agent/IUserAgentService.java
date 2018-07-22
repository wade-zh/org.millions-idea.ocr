/***
 * @pName mi-ocr-web-app
 * @name IUserAgentService
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */
package org.millions.idea.ocr.web.agent;

import feign.Headers;
import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient("${services.common}")
@Headers({"Content-Type: application/json","Accept: application/json"})
public interface IUserAgentService {

    @RequestMapping(value = "/web/login", method = RequestMethod.POST)
    Integer webLogin(String username,
                      String password,
                      String vcode);
}
