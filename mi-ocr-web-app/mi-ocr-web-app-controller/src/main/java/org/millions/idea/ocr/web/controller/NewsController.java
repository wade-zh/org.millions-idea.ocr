/***
 * @pName mi-ocr-web-app
 * @name NewsController
 * @user HongWei
 * @date 2018/7/15
 * @desc
 */
package org.millions.idea.ocr.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
public class NewsController {

    @GetMapping(value = {"","/index"})
    public String index(){
        return "news/index";
    }
}
