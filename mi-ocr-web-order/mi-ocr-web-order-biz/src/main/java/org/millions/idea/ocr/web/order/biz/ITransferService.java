/***
 * @pName mi-ocr-web-order
 * @name ITransferService
 * @user HongWei
 * @date 2018/6/29
 * @desc
 */
package org.millions.idea.ocr.web.order.biz;

import org.millions.idea.ocr.web.common.entity.common.HttpResp;

public interface ITransferService {
    /**
     * 系统扣费方法
     * @param recvUid
     * @param amount
     * @return
     */
    boolean transfer(Integer recvUid, Double amount);
}
