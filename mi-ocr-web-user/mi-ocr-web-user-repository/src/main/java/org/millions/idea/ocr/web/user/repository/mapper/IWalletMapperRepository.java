/***
 * @pName mi-ocr-web-user
 * @name IWalletMapperRepository
 * @user HongWei
 * @date 2018/6/22
 * @desc
 */
package org.millions.idea.ocr.web.user.repository.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IWalletMapperRepository {
    /**
     * Reduce users wallet balance
     * @param uid
     * @return
     */
    boolean Reduce(Integer uid);
}
