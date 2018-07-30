package org.millions.idea.ocr.web.biz;

import org.millions.idea.ocr.web.entity.agent.UserEntity;

/***
 * @pName mi-ocr-web-app
 * @name IUserService
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */

public interface IUserService {
    UserEntity login(String username, String ip);

    Boolean register(String username, String password, String email);
}
