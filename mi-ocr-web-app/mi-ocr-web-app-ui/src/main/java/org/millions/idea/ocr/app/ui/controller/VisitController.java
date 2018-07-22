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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VisitController {

    @GetMapping(value = {"/","/index"})
    public String index(){
        return "visit/index";
    }

    @GetMapping(value = {"/login"})
    public String login(){return "visit/login";}

    @GetMapping(value = {"/signin"})
    public String signin(){return "visit/signin";}

}
