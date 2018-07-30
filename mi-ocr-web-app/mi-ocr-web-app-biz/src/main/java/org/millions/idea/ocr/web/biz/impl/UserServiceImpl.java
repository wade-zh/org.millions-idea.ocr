/***
 * @pName mi-ocr-web-app
 * @name UserServiceImpl
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */
package org.millions.idea.ocr.web.biz.impl;

import org.millions.idea.ocr.web.entity.agent.UserEntity;
import org.millions.idea.ocr.web.agent.IUserAgentService;
import org.millions.idea.ocr.web.biz.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserAgentService userAgentService;

    @Override
    public UserEntity login(String username, String ip) {
        return userAgentService.webLogin(username, ip);
    }

    @Override
    public Boolean register(String username, String password, String email) {
        return userAgentService.addUser(username, password, email);
    }
}
