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
import org.millions.idea.ocr.web.user.entity.db.Wallet;
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
    boolean reduce(@Param("uid") Integer uid,@Param("channel") String channel,@Param("version") Integer version);

    /**
     * Select users wallet information
     * @param uid
     * @return
     */
    Wallet select(@Param("uid") Integer uid);

    boolean update(@Param("uid") Integer uid, @Param("balance") Integer balance);
}
