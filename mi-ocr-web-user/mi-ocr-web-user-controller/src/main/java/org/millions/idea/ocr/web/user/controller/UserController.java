/***
 * @pName mi-ocr-web-user
 * @name UserController
 * @user HongWei
 * @date 2018/6/24
 * @desc
 */
package org.millions.idea.ocr.web.user.controller;

import com.netflix.ribbon.proxy.annotation.Http;
import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.millions.idea.ocr.web.user.biz.IUserService;
import org.millions.idea.ocr.web.user.entity.common.LoginResult;
import org.millions.idea.ocr.web.user.entity.db.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public HttpResp login(String uname, String pwd){
        return new HttpResp(0,userService.login(uname, pwd));
    }

    @GetMapping("/getUserByUid")
    public Users getUserByUid(Integer uid){
        return userService.getUser(uid);
    }
}
