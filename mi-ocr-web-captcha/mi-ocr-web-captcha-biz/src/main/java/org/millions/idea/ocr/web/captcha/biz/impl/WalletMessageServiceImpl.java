/***
 * @pName mi-ocr-web-captcha
 * @name WalletMessageServiceImpl
 * @user HongWei
 * @date 2018/6/24
 * @desc
 */
package org.millions.idea.ocr.web.captcha.biz.impl;

import com.rabbitmq.client.*;
import org.millions.idea.ocr.web.captcha.agent.IWalletAgentService;
import org.millions.idea.ocr.web.captcha.biz.IWalletMessageService;
import org.millions.idea.ocr.web.captcha.utility.json.JsonUtil;
import org.millions.idea.ocr.web.common.entity.common.WalletReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class WalletMessageServiceImpl implements IWalletMessageService{

    @Autowired
    private IWalletAgentService walletAgentService;

    @Override
    public void onMessage(Channel channel,Envelope envelope, String message){
        try {
            WalletReq model = JsonUtil.getModel(message, WalletReq.class);
            walletAgentService.reduce(model.getUid(), model.getChannel());
            channel.basicAck(envelope.getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
