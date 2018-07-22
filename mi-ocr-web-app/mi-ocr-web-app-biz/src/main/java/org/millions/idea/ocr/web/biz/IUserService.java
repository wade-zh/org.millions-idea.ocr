package org.millions.idea.ocr.web.biz;

/***
 * @pName mi-ocr-web-app
 * @name IUserService
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */

public interface IUserService {
    boolean login(String username, String password, String vcode);
}
