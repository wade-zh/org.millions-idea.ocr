/***
 * @pName mi-ocr-web-app
 * @name UserServiceImpl
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */
package org.millions.idea.ocr.web.biz.impl;

import org.millions.idea.ocr.web.agent.IWalletAgentService;
import org.millions.idea.ocr.web.entity.agent.UserDetailEntity;
import org.millions.idea.ocr.web.entity.agent.UserEntity;
import org.millions.idea.ocr.web.agent.IUserAgentService;
import org.millions.idea.ocr.web.biz.IUserService;
import org.millions.idea.ocr.web.entity.agent.WalletEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserAgentService userAgentService;

    @Autowired
    private IWalletAgentService walletAgentService;

    @Override
    public UserDetailEntity login(String username, String ip) {
        return userAgentService.webLogin(username, ip);
    }

    @Override
    public Boolean register(String username, String password, String email) {
        return userAgentService.addUser(username, password, email);
    }

    /**
     * 查询用户余额
     *
     * @param uid
     * @return
     */
    @Override
    public BigDecimal getBalance(Integer uid) {
        WalletEntity wallet = walletAgentService.get(uid);
        return wallet.getBalance();
    }
}
