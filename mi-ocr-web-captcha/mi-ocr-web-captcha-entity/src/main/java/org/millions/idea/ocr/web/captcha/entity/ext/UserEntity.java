/***
 * @pName mi-ocr-web-captcha
 * @name UserEntity
 * @user HongWei
 * @date 2018/6/25
 * @desc
 */
package org.millions.idea.ocr.web.captcha.entity.ext;


import org.millions.idea.ocr.web.captcha.entity.db.Users;
import org.millions.idea.ocr.web.captcha.entity.db.Wallet;

import java.sql.Timestamp;


public class UserEntity extends Users {
    private String token;
    private Wallet wallet;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public UserEntity() {

    }

    public UserEntity(Integer uid, String userName, String password, Integer issueId, String issueResult, String contact, Timestamp registTime, Timestamp lastActiveTime, String lastLoginIp, String token, Wallet wallet) {
        super(uid, userName, password, issueId, issueResult, contact, registTime, lastActiveTime, lastLoginIp);
        this.token = token;
        this.wallet = wallet;
    }

    public UserEntity(String token, Wallet wallet) {
        this.token = token;
        this.wallet = wallet;
    }
}
