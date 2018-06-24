/***
 * @pName mi-ocr-web-common-entity
 * @name WalletReq
 * @user HongWei
 * @date 2018/6/24
 * @desc
 */
package org.millions.idea.ocr.web.common.entity.common;

public class WalletReq {
    private Integer uid;
    private String channel;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public WalletReq() {

    }

    public WalletReq(Integer uid, String channel) {

        this.uid = uid;
        this.channel = channel;
    }
}
