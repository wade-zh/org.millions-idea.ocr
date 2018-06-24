/***
 * @pName org.millions-idea.ocr
 * @name UploadController
 * @user HongWei
 * @date 2018/6/6
 * @desc Receive user's binary image
 */
package org.millions.idea.ocr.web.captcha.controller;

import org.millions.idea.ocr.web.captcha.biz.IMessageService;
import org.millions.idea.ocr.web.captcha.entity.UploadCaptchaReq;
import org.millions.idea.ocr.web.captcha.entity.types.HttpErrorCodeType;
import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class CaptchaController {
    @Autowired
    @Qualifier(value = "MessageServiceImpl")
    private IMessageService messageServiceImpl;

    @Autowired
    @Qualifier(value = "PublishMessageServiceImpl")
    private IMessageService publishMessageServiceImpl;

    @RequestMapping("upload")
    public HttpResp upload(@RequestParam("file") MultipartFile file,
                           @RequestParam("channel") String channel,
                           @RequestParam("token") String token,
                           @RequestParam("uname") String uname,
                           @RequestParam("pwd") String pwd){
        try {
            return new HttpResp(-1, publishMessageServiceImpl.publish(new UploadCaptchaReq(uname, pwd, token, file.getBytes(), channel)));
        } catch (IOException e) {
            return new HttpResp(HttpErrorCodeType.IOException.ordinal(), HttpErrorCodeType.IOException.toString());
        }
    }

    @PostMapping("getCaptcha")
    public HttpResp getCaptcha(String captchaId){
        String code = messageServiceImpl.getCaptcha(captchaId);
        if(code != null) return new HttpResp(HttpErrorCodeType.SUCCESS.ordinal(), code);
        return new HttpResp(HttpErrorCodeType.WAIT.ordinal(), null);
    }

    @GetMapping("report")
    public void report(String captchaId){
        if(captchaId == null || captchaId.length() == 0) return;
        publishMessageServiceImpl.publish(captchaId);
    }
}
