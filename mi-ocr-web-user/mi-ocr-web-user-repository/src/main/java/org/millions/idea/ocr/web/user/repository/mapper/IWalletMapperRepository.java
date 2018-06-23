/***
 * @pName mi-ocr-web-user
 * @name IWalletMapperRepository
 * @user HongWei
 * @date 2018/6/22
 * @desc
 */
package org.millions.idea.ocr.web.user.repository.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface IWalletMapperRepository {
    /**
     * Reduce users wallet balance
     * @param uid
     * @param channel
     * @return
     */
    boolean Reduce(@Param("uid") Integer uid, @Param("channel") String channel);
}
