/***
 * @pName mi-ocr-web-captcha
 * @name UserEntity
 * @user HongWei
 * @date 2018/6/25
 * @desc
 */
package org.millions.idea.ocr.web.captcha.entity.ext;


import org.millions.idea.ocr.web.captcha.entity.agent.order.WalletEntity;
import org.millions.idea.ocr.web.captcha.entity.db.Users;

import java.sql.Timestamp;


public class UserEntity extends Users {
    private String token;
    private WalletEntity walletEntity;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public WalletEntity getWalletEntity() {
        return walletEntity;
    }

    public void setWalletEntity(WalletEntity walletEntity) {
        this.walletEntity = walletEntity;
    }

    public UserEntity() {

    }

    public UserEntity(Integer uid, String userName, String password, Integer issueId, String issueResult, String contact, Timestamp registTime, Timestamp lastActiveTime, String lastLoginIp, String token, WalletEntity walletEntity) {
        super(uid, userName, password, issueId, issueResult, contact, registTime, lastActiveTime, lastLoginIp);
        this.token = token;
        this.walletEntity = walletEntity;
    }

    public UserEntity(String token, WalletEntity walletEntity) {
        this.token = token;
        this.walletEntity = walletEntity;
    }
}
