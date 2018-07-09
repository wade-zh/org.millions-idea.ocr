/***
 * @pName mi-ocr-web-order
 * @name WalletController
 * @user HongWei
 * @date 2018/7/4
 * @desc
 */
package org.millions.idea.ocr.web.order.controller;

import org.millions.idea.ocr.web.common.utility.date.DateUtil;
import org.millions.idea.ocr.web.order.biz.order.IWalletService;
import org.millions.idea.ocr.web.order.entity.db.WalletEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private IWalletService walletService;

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public WalletEntity get(Integer uid){
        WalletEntity entity = walletService.getWallet(uid);
        entity.setEditDate(null);
        return entity;
    }
}
