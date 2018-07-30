/***
 * @pName mi-ocr-web-user
 * @name UserController
 * @user HongWei
 * @date 2018/6/24
 * @desc
 */
package org.millions.idea.ocr.web.user.controller;

import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.millions.idea.ocr.web.user.biz.IUserService;
import org.millions.idea.ocr.web.user.entity.db.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 直接登录账户
     * @param uname
     * @param pwd
     * @return
     */
    @PostMapping("/login")
    public HttpResp directLogin(String uname, String pwd){
        return new HttpResp(0,userService.directLogin(uname, pwd));
    }

    /**
     * 通过web渠道登录账户
     * @param username
     * @return
     */
    @PostMapping("/web/login")
    public Users webLogin(String username, String ip){
        return userService.webLogin(username, ip);
    }

    /**
     * 通过uid查询用户信息
     * @param uid
     * @return
     */
    @GetMapping("/getUserByUid")
    public Users getUserByUid(Integer uid){
        return userService.getUser(uid);
    }

    /**
     * 通过key关联查询余额
     * @param token
     * @return
     */
    @GetMapping("/getBalance")
    public HttpResp getBalance(String token){
        return new HttpResp(1,String.valueOf(userService.getBalance(token)));
    }

    /**
     * 添加新用户
     * @param user
     * @return
     */
    @PostMapping("/web/addUser")
    public Boolean addUser(Users user){
        return userService.addUser(user);
    }
}
