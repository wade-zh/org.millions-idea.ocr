/***
 * @pName mi-ocr-web-captcha
 * @name IUserAgentService
 * @user HongWei
 * @date 2018/6/24
 * @desc
 */
package org.millions.idea.ocr.web.captcha.agent.user;

import feign.Headers;
import org.millions.idea.ocr.web.captcha.entity.db.Users;
import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("${services.user}")
@Headers("Content-Type: application/json")
public interface IUserAgentService {
    @GetMapping("/getUserByUid")
    Users getUserByUid(Integer uid);

    @PostMapping("/login")
    HttpResp login(@RequestParam("uname") String uname, @RequestParam("pwd") String pwd);
}
