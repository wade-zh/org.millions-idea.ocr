/***
 * @pName mi-ocr-web-finance
 * @name FinanceController
 * @user HongWei
 * @date 2018/6/29
 * @desc
 */
package org.millions.idea.ocr.web.order.controller;


import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.millions.idea.ocr.web.order.biz.order.IPayService;
import org.millions.idea.ocr.web.order.entity.agent.PayParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private IPayService payService;

    /**
     * 兑换货币
     * @param payParam
     * @return
     */
    @PostMapping("/exchange")
    public HttpResp exchange(PayParam payParam){
        String tradeId = (String) payService.exchange(payParam);
        return new HttpResp(0, tradeId);
    }

    /**
     * 生成交易流水
     * @param payParam
     * @return
     */
    @PostMapping("/addTradeRecord")
    public HttpResp addTradeRecord(@RequestBody PayParam payParam){
        return new HttpResp(0, payService.addTradeRecord(payParam));
    }

    /**
     * 用户消费
     * @param payParam
     * @return
     */
    @PostMapping("/buy")
    public HttpResp buy(@RequestBody  PayParam payParam){
        payService.buyCallable(payParam);
        return new HttpResp(0, HttpResp.RespCode.SUCCESS.getCode().toString());
    }

    @GetMapping("/isExitsTradeRecord")
    public HttpResp isExitsTradeRecord(String captchaId){
        boolean result = payService.isExitsTradeRecord(captchaId);
        return new HttpResp(result ? 0 : 1, result ? HttpResp.RespCode.SUCCESS.getCode().toString() : HttpResp.RespCode.FAILD.getCode().toString());
    }
}
