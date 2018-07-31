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
import org.millions.idea.ocr.web.config.security.UserDetailsEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

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

    @GetMapping("/api/getBalance")
    public HttpResp getBalance(){
        UserDetailsEx details = (UserDetailsEx) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer uid = details.getDetail().getUid();
        BigDecimal balance = userService.getBalance(uid);
        details.getDetail().setBalance(balance);
        return new HttpResp(0, balance.toString());
    }
}
