/***
 * @pName org.millions-idea.ocr
 * @name UploadController
 * @user HongWei
 * @date 2018/6/6
 * @desc Receive user's binary image
 */
package org.millions.idea.ocr.web.captcha.controller;

import org.millions.idea.ocr.web.captcha.biz.IMessageService;
import org.millions.idea.ocr.web.captcha.entity.types.HttpErrorCodeType;
import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
                           @RequestParam("channel") String channel){
        try {
            return new HttpResp(-1, publishMessageServiceImpl.publish(file.getBytes(), channel));
        } catch (IOException e) {
            return new HttpResp(HttpErrorCodeType.IOException.ordinal(), HttpErrorCodeType.IOException.toString());
        }
    }

    @RequestMapping("getCaptcha")
    public HttpResp getCaptcha(String cid){
        String code = messageServiceImpl.getCaptcha(cid);
        if(code != null) return new HttpResp(-1, code);
        return new HttpResp(HttpErrorCodeType.NotResult.ordinal(), HttpErrorCodeType.NotResult.toString());
    }

    @RequestMapping("report")
    public void report(String cid){
        if(cid == null || cid.length() == 0) return;
        publishMessageServiceImpl.publish(cid);
    }
}
