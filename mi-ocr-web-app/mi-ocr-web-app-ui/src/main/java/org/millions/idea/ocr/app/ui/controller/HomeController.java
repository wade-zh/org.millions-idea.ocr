/***
 * @pName mi-ocr-web-app
 * @name HomeController
 * @user HongWei
 * @date 2018/7/11
 * @desc
 */
package org.millions.idea.ocr.app.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"/","/index"})
    public String index(){
        return "un_login/index";
    }

    @GetMapping(value = {"/login"})
    public String login(){return "un_login/login";}

    @GetMapping(value = {"/signin"})
    public String signin(){return "un_login/signin";}
}
