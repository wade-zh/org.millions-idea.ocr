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
     * @param token
     * @param channel
     * @return
     */
    boolean reduce(String token, String channel);

    boolean reduce(String token);
}
