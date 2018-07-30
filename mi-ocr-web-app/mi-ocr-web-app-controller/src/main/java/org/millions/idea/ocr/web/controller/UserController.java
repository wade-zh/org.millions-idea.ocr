/***
 * @pName mi-ocr-web-app
 * @name UserController
 * @user HongWei
 * @date 2018/7/12
 * @desc
 */
package org.millions.idea.ocr.web.controller;

import org.millions.idea.ocr.web.config.security.UserDetailsEx;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping(value = {"","/index"})
    public String index(final Model model){
        UserDetailsEx userDetails = (UserDetailsEx) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("city",userDetails.getDetail().getLastLoginArea());
        model.addAttribute("ip",userDetails.getDetail().getLastLoginIp());
        return "user/index";
    }
}
