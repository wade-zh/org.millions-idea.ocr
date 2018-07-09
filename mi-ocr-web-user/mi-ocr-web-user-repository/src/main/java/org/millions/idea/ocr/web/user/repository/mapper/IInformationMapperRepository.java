/***
 * @pName mi-ocr-web-user
 * @name IInformationMapperRepository
 * @user HongWei
 * @date 2018/6/29
 * @desc
 */
package org.millions.idea.ocr.web.user.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IInformationMapperRepository {
    /**
     * 更新用户余额
     * @param uid
     * @param channel
     * @return
     */
    boolean updateBalance(Integer uid, String channel);
}
