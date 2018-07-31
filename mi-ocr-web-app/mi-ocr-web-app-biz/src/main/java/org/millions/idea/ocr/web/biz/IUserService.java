package org.millions.idea.ocr.web.biz;

import org.millions.idea.ocr.web.entity.agent.UserDetailEntity;
import org.millions.idea.ocr.web.entity.agent.UserEntity;

import java.math.BigDecimal;

/***
 * @pName mi-ocr-web-app
 * @name IUserService
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */

public interface IUserService {
    UserDetailEntity login(String username, String ip);

    Boolean register(String username, String password, String email);

    /**
     * 查询用户余额
     * @param uid
     * @return
     */
    BigDecimal getBalance(Integer uid);
}
