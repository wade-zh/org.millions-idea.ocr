/***
 * @pName security
 * @name TestController
 * @user HongWei
 * @date 2018/7/15
 * @desc
 */
package com.example.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    public String hello(){
        return "Hello security";
    }


}
