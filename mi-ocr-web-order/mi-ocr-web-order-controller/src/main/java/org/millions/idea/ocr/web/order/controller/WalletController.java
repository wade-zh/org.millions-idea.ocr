/***
 * @pName mi-ocr-web-order
 * @name WalletController
 * @user HongWei
 * @date 2018/7/4
 * @desc
 */
package org.millions.idea.ocr.web.order.controller;

import org.millions.idea.ocr.web.order.biz.order.IWalletService;
import org.millions.idea.ocr.web.order.entity.db.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private IWalletService walletService;

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public Wallet get(Integer uid){
        Wallet entity = walletService.getWallet(uid);
        entity.setEditDate(null);
        return entity;
    }


    @GetMapping("/addNewWallet")
    public Integer addNewWallet(Integer uid){
        return walletService.addNewWallet(uid);
    }
}
