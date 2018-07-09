/***
 * @pName mi-ocr-web-order
 * @name ITransationRecordMapperRepository
 * @user HongWei
 * @date 2018/6/30
 * @desc
 */
package org.millions.idea.ocr.web.order.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.millions.idea.ocr.web.order.entity.db.TransactionRecordEntity;

import java.util.Map;

@Mapper
public interface ITransactionRecordMapperRepository {
    int insert(TransactionRecordEntity entity);

    void buy(Map map);

    void delayedBuy(Map map);

    TransactionRecordEntity selectByCaptchaId(@Param("captchaId") String captchaId);
}
