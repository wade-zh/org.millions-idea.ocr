/***
 * @pName mi-ocr-web-captcha
 * @name WalletMessageServiceImpl
 * @user HongWei
 * @date 2018/6/24
 * @desc
 */
package org.millions.idea.ocr.web.captcha.biz.impl;

import com.rabbitmq.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.millions.idea.ocr.web.captcha.agent.IWalletAgentService;
import org.millions.idea.ocr.web.captcha.biz.IWalletMessageService;
import org.millions.idea.ocr.web.captcha.entity.db.Users;
import org.millions.idea.ocr.web.captcha.entity.ext.UserEntity;
import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.millions.idea.ocr.web.common.entity.common.WalletReq;
import org.millions.idea.ocr.web.common.entity.exception.MessageException;
import org.millions.idea.ocr.web.common.utility.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WalletMessageServiceImpl implements IWalletMessageService{
    final static Logger logger = LogManager.getLogger(WalletMessageServiceImpl.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IWalletAgentService walletAgentService;

    @Override
    @Async
    public void onMessage(Channel channel,Envelope envelope, String message){
        try {
            WalletReq model = JsonUtil.getModel(message, WalletReq.class);
            logger.info("扣费参数:" + message);
            HttpResp result = walletAgentService.reduce(model.getToken(), model.getChannel());
            logger.info("扣费结果:" + JsonUtil.getJson(result));
            if(result.getError() == 0) {
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        } catch (Exception e) {
            logger.error("扣费异常:" + e.getCause().getMessage());
        }
    }
}
