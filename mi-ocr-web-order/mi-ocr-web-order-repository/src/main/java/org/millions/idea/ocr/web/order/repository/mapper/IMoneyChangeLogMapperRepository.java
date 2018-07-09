/***
 * @pName mi-ocr-web-order
 * @name IMoneyChangeLogMapperRepository
 * @user HongWei
 * @date 2018/6/30
 * @desc
 */
package org.millions.idea.ocr.web.order.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.millions.idea.ocr.web.order.entity.db.MoneyChangeLogEntity;

@Mapper
public interface IMoneyChangeLogMapperRepository {
    int insert(MoneyChangeLogEntity entity);
}
