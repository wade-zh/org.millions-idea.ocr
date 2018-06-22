/***
 * @pName mi-ocr-web-user
 * @name IUserService
 * @user HongWei
 * @date 2018/6/22
 * @desc
 */
package org.millions.idea.ocr.web.user.biz;

import org.millions.idea.ocr.web.user.entity.db.Users;

import java.util.List;

public interface IUserService {
    /**
     * Get all user information
     * @return
     */
    List<Users> getUsers();

    /**
     * Get user information by uid
     * @param uid
     * @return
     */
    Users getUser(Integer uid);
}
