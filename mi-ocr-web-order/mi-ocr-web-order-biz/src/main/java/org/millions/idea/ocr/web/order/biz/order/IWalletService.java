/***
 * @pName mi-ocr-web-order
 * @name IWalletService
 * @user HongWei
 * @date 2018/7/4
 * @desc
 */
package org.millions.idea.ocr.web.order.biz.order;

import org.millions.idea.ocr.web.order.entity.db.Wallet;

public interface IWalletService {
    /**
     * 查询wallet信息
     * @param uid
     * @return
     */
    Wallet getWallet(Integer uid);

    /**
     * 添加新钱包并返回主键 韦德 2018年8月1日10:13:25
     * @param uid
     */
    Integer addNewWallet(Integer uid);
}
