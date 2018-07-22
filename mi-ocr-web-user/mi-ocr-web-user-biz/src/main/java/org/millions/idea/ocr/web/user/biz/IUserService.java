/***
 * @pName mi-ocr-web-user
 * @name IUserService
 * @user HongWei
 * @date 2018/6/22
 * @desc
 */
package org.millions.idea.ocr.web.user.biz;

import org.millions.idea.ocr.web.common.entity.common.HttpResp;
import org.millions.idea.ocr.web.user.entity.common.LoginResult;
import org.millions.idea.ocr.web.user.entity.db.Users;

import java.math.BigDecimal;
import java.util.List;

public interface IUserService {

    /**
     * 根据uid查询用户信息
     * @param uid
     * @return
     */
    Users getUser(Integer uid);

    /**
     * 直接登录账户
     * @param uname
     * @param pwd
     * @return
     */
    String directLogin(String uname, String pwd);

    /**
     * 通过web渠道登录账户
     * @param username
     * @param password
     * @param vcode
     * @return
     */
    Integer webLogin(String username, String password, String vcode);


    /**
     * 查询余额
     * @param token
     * @return
     */
    BigDecimal getBalance(String token);
}
