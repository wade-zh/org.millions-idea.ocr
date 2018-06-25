/***
 * @pName mi-ocr-web-user
 * @name IBackCategoryService
 * @user HongWei
 * @date 2018/6/25
 * @desc
 */
package org.millions.idea.ocr.web.user.biz;

import org.millions.idea.ocr.web.user.entity.db.Backcategorys;

import java.util.List;

public interface IBackCategoryService {
    /**
     * Query all categorys
     * @return
     */
    List<Backcategorys> queryAll();

    /**
     * Write categorys in cache
     */
    void initCache();
}
