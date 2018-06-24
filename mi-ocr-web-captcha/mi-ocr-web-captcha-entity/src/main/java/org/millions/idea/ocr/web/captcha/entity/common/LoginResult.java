/***
 * @pName mi-ocr-web-captcha
 * @name LoginResult
 * @user HongWei
 * @date 2018/6/24
 * @desc
 */
package org.millions.idea.ocr.web.captcha.entity.common;


import org.millions.idea.ocr.web.captcha.entity.db.Users;

public class LoginResult {
    private Users user;
    private String token;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginResult() {
    }

    public LoginResult(Users user, String token) {

        this.user = user;
        this.token = token;
    }
}
