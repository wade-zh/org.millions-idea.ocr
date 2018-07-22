/***
 * @pName mi-ocr-web-app
 * @name UserApiController
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */
package org.millions.idea.ocr.app.ui.controller.api;

import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @PostMapping("/api/web-login")
    public HttpResp webLogin(String username, String password, String vcode) {
        /*return userAgentService.webLogin(username, password, vcode);*/
        return null;
    }
}
