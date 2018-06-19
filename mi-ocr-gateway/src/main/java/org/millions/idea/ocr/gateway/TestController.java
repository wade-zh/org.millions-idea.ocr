/***
 * @pName mi-ocr-gateway
 * @name TestController
 * @user HongWei
 * @date 2018/6/19
 * @desc
 */
package org.millions.idea.ocr.gateway;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/connect")
    public String test(){
        return "ok";
    }
}
