/***
 * @pName mi-ocr-web-user
 * @name IWalletService
 * @user HongWei
 * @date 2018/6/23
 * @desc
 */
package org.millions.idea.ocr.web.user.biz;

public interface IWalletService {
    /**
     * Reduce users wallet balance
     * @param uid
     * @param channel
     * @return
     */
    boolean reduce(Integer uid, String channel);
}
