/***
 * @pName mi-ocr-common-entity
 * @name Captcha
 * @user HongWei
 * @date 2018/6/18
 * @desc
 */
package org.millions.idea.ocr.common.entity;

public class Captcha {
    private String ticket;
    private String channel;
    private byte[] binary;

    public Captcha(String ticket, String channel, byte[] binary) {
        this.ticket = ticket;
        this.channel = channel;
        this.binary = binary;
    }

    public String getTicket() {

        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public byte[] getBinary() {
        return binary;
    }

    public void setBinary(byte[] binary) {
        this.binary = binary;
    }
}
