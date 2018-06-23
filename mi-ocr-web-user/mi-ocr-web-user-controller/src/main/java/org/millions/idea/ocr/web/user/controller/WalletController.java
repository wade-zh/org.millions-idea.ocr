/***
 * @pName mi-ocr-web-user-parent
 * @name AssetController
 * @user HongWei
 * @date 2018/6/20
 * @desc
 */
package org.millions.idea.ocr.web.user.controller;

import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.millions.idea.ocr.web.user.biz.IUserService;
import org.millions.idea.ocr.web.user.biz.IWalletService;
import org.millions.idea.ocr.web.user.entity.db.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private IWalletService walletService;

    @RequestMapping(value = "/reduce", method = RequestMethod.POST)
    public HttpResp reduce(Integer uid, String channel){
        boolean result = walletService.reduce(uid, channel);
        if(result) return new HttpResp(0, HttpResp.RespCode.SUCCESS.toString());
        return new HttpResp(1, HttpResp.RespCode.FAILD.toString());
    }
}
