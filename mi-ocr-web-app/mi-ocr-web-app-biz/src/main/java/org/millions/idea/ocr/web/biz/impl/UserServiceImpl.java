/***
 * @pName mi-ocr-web-app
 * @name UserServiceImpl
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */
package org.millions.idea.ocr.web.biz.impl;

import org.millions.idea.ocr.web.agent.IUserAgentService;
import org.millions.idea.ocr.web.biz.IUserService;
import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserAgentService userAgentService;

    @Override
    public boolean login(String username, String password, String vcode) {
        Integer userId = userAgentService.webLogin(username, password, vcode);
        return userId != 0;
    }
}
