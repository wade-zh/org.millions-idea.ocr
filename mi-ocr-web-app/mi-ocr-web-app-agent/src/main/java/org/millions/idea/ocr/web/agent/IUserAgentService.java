/***
 * @pName mi-ocr-web-app
 * @name IUserAgentService
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */
package org.millions.idea.ocr.web.agent;

import feign.Headers;
import org.millions.idea.ocr.web.entity.agent.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("${services.user}")
@Headers({"Content-Type: application/json","Accept: application/json"})
public interface IUserAgentService {

    @RequestMapping(value = "/web/login", method = RequestMethod.POST)
    UserEntity webLogin(@RequestParam("username") String username, @RequestParam("ip") String ip);
}
