/***
 * @pName mi-ocr-web-order
 * @name FinanceController
 * @user HongWei
 * @date 2018/6/29
 * @desc
 */
package org.millions.idea.ocr.web.order.controller;


import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.millions.idea.ocr.web.order.biz.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FinanceController {
    @Autowired
    private ITransferService transferService;

    @GetMapping("/reduce")
    public HttpResp reduce(Integer uid, Double amount){
        transferService.transfer(uid, amount);
        return new HttpResp(0, HttpResp.RespCode.SUCCESS.getCode());
    }
}
