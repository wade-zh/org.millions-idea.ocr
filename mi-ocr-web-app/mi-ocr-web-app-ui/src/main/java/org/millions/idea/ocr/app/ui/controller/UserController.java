/***
 * @pName mi-ocr-web-app
 * @name UserController
 * @user HongWei
 * @date 2018/7/12
 * @desc
 */
package org.millions.idea.ocr.app.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {


    @GetMapping(value = {"","/index"})
    public String index(){return "user/index";}

}
