/***
 * @pName mi-ocr-web-captcha
 * @name IUserAgentService
 * @user HongWei
 * @date 2018/6/24
 * @desc
 */
package org.millions.idea.ocr.web.captcha.agent;

import feign.RequestLine;
import org.millions.idea.ocr.web.captcha.entity.common.LoginResult;
import org.millions.idea.ocr.web.captcha.entity.db.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("${services.user}")
public interface IUserAgentService {
    @GetMapping("/getUserByUid")
    Users getUserByUid(Integer uid);

    @PostMapping("/login")
    LoginResult login(String uname, String pwd);
}
