/***
 * @pName mi-ocr-web-user
 * @name IUserCacheRepository
 * @user HongWei
 * @date 2018/6/22
 * @desc
 */
package org.millions.idea.ocr.web.user.repository;

import org.millions.idea.ocr.web.user.entity.db.Users;

public interface IUserCacheRepository {
    /**
     * Query user information in this way.
     * @param uid
     * @return
     */
    Users query(Integer uid);
}
