/***
 * @pName mi-ocr-web-common-entity
 * @name WalletReq
 * @user HongWei
 * @date 2018/6/24
 * @desc
 */
package org.millions.idea.ocr.web.common.entity.common;

public class WalletReq {
    private String channel;
    private String token;

    public WalletReq(String channel, String token) {
        this.channel = channel;
        this.token = token;
    }

    public String getToken() {

        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public WalletReq() {

    }


}
