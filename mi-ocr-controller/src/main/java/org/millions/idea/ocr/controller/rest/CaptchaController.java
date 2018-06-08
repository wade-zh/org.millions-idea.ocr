/***
 * @pName org.millions-idea.ocr
 * @name UploadController
 * @user HongWei
 * @date 2018/6/6
 * @desc Receive user's binary image
 */
package org.millions.idea.ocr.controller.rest;

import org.millions.idea.ocr.utility.queue.RabbitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("captcha")
public class CaptchaController {

    @Value("${server.port}")
    private String instancePort;

    @Autowired
    private RabbitUtil rabbitUtil;

    @RequestMapping("upload")
    public String upload(){
        rabbitUtil.publish("hello");
        System.out.println("At your service " + instancePort);
        return "At your service " + instancePort;
    }
}
