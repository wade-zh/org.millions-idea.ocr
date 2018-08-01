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

import java.sql.Timestamp;
import java.util.List;

@Mapper
@Component
public interface IUserMapperRepository {
    /**
     * Query by uid
     * @param uid
     * @return
     */
    Users select(@Param("uid") Integer uid);

    /**
     * Query by username
     * @param username
     * @return
     */
    Users selectUserByUsername(@Param("username") String username);

    /**
     * Query users information
     * @param uname
     * @param pwd
     * @return
     */
    Users login(@Param("uname") String uname, @Param("pwd") String pwd);


    /**
     * 更新用户最后活动信息
     * @param username
     * @param lastActiveTime
     * @param lastLoginIp
     * @return
     */
    int updateActive(@Param("username") String username,
                     @Param("lastActiveTime") Timestamp lastActiveTime,
                     @Param("lastLoginIp") String lastLoginIp,
                     @Param("lastLoginArea") String lastLoginArea);


    /**
     * 添加用户并返回主键 韦德 2018年8月1日10:11:43
     * @param user
     * @return
     */
    int insert(Users user);

    /**
     * 删除记录
     * @param uid
     * @return
     */
    int delete(@Param("uid") Integer uid);
}
