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
import org.millions.idea.ocr.web.order.entity.agent.OrderDetailEntity;
import org.millions.idea.ocr.web.order.entity.db.TransactionRecord;

import java.util.List;
import java.util.Map;

@Mapper
public interface ITransactionRecordMapperRepository {
    int insert(TransactionRecord entity);

    void buy(Map map);

    void report(Map map);

    void delayedBuy(Map map);

    /**
     * 根据captchaId查询归属订单 韦德 2018年6月1日14:00:02
     * @param captchaId
     * @return
     */
    TransactionRecord selectByCaptchaId(@Param("captchaId") String captchaId);

    /**
     * 根据uid查询近期订单集合(当天与昨天) 韦德 2018年7月31日22:59:24
     * @param uid
     * @return
     */
    List<OrderDetailEntity> selectRecentOrders(@Param("uid") Integer uid);
}
