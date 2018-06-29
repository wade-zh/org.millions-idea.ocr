/***
 * @pName mi-ocr-web-order
 * @name ITransferMapperRepository
 * @user HongWei
 * @date 2018/6/29
 * @desc
 */
package org.millions.idea.ocr.web.order.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.millions.idea.ocr.web.order.entity.db.Transfers;

@Mapper
public interface ITransferMapperRepository {
    /**
     * 转账给指定用户
     * @param transfer
     * @return
     */
    int transfer(Transfers transfer);
}
