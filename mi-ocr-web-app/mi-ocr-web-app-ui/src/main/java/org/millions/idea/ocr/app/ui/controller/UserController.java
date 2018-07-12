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

@Controller
public class UserController {

    @GetMapping(value = {"/user","/user/index"})
    public String index(){return "login/index";}


}
