/***
 * @pName mi-ocr-web-user
 * @name IBackCategoryService
 * @user HongWei
 * @date 2018/6/25
 * @desc
 */
package org.millions.idea.ocr.web.user.biz;

import org.millions.idea.ocr.web.user.entity.db.Channels;

import java.util.List;

public interface IChannelsService {
    /**
     * Query all categorys
     * @return
     */
    List<Channels> queryAll();

    /**
     * Write categorys in cache
     */
    void initCache();
}
