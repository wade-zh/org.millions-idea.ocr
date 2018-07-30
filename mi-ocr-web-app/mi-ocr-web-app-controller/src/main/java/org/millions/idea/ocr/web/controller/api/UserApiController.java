/***
 * @pName mi-ocr-web-app
 * @name UserApiController
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */
package org.millions.idea.ocr.web.controller.api;

import org.millions.idea.ocr.web.biz.IUserService;
import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @Autowired
    private IUserService userService;

    @PostMapping("/api/register")
    public HttpResp register(String username, String password, String email) {
        Boolean result = userService.register(username, password, email);
        if (result) return new HttpResp(0, HttpResp.RespCode.SUCCESS.toString());
        return new HttpResp(1, HttpResp.RespCode.FAILD.toString());
    }
}
