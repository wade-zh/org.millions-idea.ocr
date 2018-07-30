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
import java.sql.Timestamp;
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
     * 查询余额
     * @param token
     * @return
     */
    BigDecimal getBalance(String token);

    /**
     * 根据username查询用户信息
     * @param username
     * @return
     */
    Users webLogin(String username,  String lastLoginIp);

    /**
     * 添加新用户
     * @param user
     * @return
     */
    boolean addUser(Users user);
}
