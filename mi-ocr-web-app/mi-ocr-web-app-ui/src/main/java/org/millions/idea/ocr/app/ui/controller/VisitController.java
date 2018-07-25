/***
 * @pName mi-ocr-web-app
 * @name HomeController
 * @user HongWei
 * @date 2018/7/11
 * @desc
 */
package org.millions.idea.ocr.app.ui.controller;

import org.millions.idea.ocr.web.common.utility.utils.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class VisitController {

    @GetMapping(value = {"/","/index"})
    public String index(){
        return "visit/index";
    }

    @RequestMapping(value = {"/signup"})
    public String login(HttpServletRequest request, HttpServletResponse response){
        return "visit/signup";
    }

    @GetMapping(value = {"/signin"})
    public String signin(){return "visit/signin";}

}
