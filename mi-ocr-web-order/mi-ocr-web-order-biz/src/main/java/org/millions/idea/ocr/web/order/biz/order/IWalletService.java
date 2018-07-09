/***
 * @pName mi-ocr-web-order
 * @name IWalletService
 * @user HongWei
 * @date 2018/7/4
 * @desc
 */
package org.millions.idea.ocr.web.order.biz.order;

import org.millions.idea.ocr.web.order.entity.db.WalletEntity;

public interface IWalletService {
    /**
     * 查询wallet信息
     * @param uid
     * @return
     */
    WalletEntity getWallet(Integer uid);
}
