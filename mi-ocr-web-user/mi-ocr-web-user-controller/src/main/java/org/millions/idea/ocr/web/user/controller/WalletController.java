/***
 * @pName mi-ocr-web-user-parent
 * @name AssetController
 * @user HongWei
 * @date 2018/6/20
 * @desc
 */
package org.millions.idea.ocr.web.user.controller;

import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.millions.idea.ocr.web.user.biz.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private IWalletService walletService;

    @PostMapping(value = "/reduce")
    public HttpResp reduce(Integer uid, String channel){
        boolean result = walletService.reduce(uid, channel);
        if(result) return new HttpResp(0, HttpResp.RespCode.SUCCESS.getCode());
        return new HttpResp(1, HttpResp.RespCode.FAILD.getCode());
    }
}
