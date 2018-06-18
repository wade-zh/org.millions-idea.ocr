/***
 * @pName mi-ocr-web
 * @name MessageServiceImpl
 * @user HongWei
 * @date 2018/6/18
 * @desc
 */
package org.millions.idea.ocr.web.biz.impl;

import org.millions.idea.ocr.web.biz.IMessageService;
import org.millions.idea.ocr.web.utility.queue.RabbitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "MessageServiceImpl")
public class MessageServiceImpl implements IMessageService {
    @Autowired
    private RabbitUtil rabbitUtil;

    @Override
    public String publish(byte[] binary, String channel) {
        return null;
    }

    @Override
    public String getCaptcha(String cid) {
        return null;
    }
}
