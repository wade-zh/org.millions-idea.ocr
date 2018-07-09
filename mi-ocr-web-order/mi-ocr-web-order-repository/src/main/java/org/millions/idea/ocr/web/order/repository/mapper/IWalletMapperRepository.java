/***
 * @pName mi-ocr-web-order
 * @name IWalletMapperRepository
 * @user HongWei
 * @date 2018/7/1
 * @desc
 */
package org.millions.idea.ocr.web.order.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.millions.idea.ocr.web.order.entity.db.WalletEntity;

import java.math.BigDecimal;

@Mapper
public interface IWalletMapperRepository {
    /**
     * 根据id查询钱包信息
     * @param uid
     * @return
     */
    WalletEntity selectByUid(@Param("uid") Integer uid);

    /**
     * 增加用户余额
     * @param uid
     * @param amount
     * @return
     */
    int addBalance(@Param("uid") Integer uid,@Param("amount") BigDecimal amount);

    /**
     * 减少用户余额
     * @param uid
     * @param amount
     * @return
     */
    int reduceBalance(@Param("uid") Integer uid, @Param("amount") BigDecimal amount, @Param("version") Integer version);
}
