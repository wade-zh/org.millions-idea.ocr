/***
 * @pName mi-ocr-web-user
 * @name IUserMapperRepository
 * @user HongWei
 * @date 2018/6/22
 * @desc
 */
package org.millions.idea.ocr.web.user.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.millions.idea.ocr.web.user.entity.db.Users;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface IUserMapperRepository {
    /**
     * Query all
     * @return
     */
    List<Users> queryList();

    /**
     * Query by uid
     * @param uid
     * @return
     */
    Users query(@Param("uid") Integer uid);
}
