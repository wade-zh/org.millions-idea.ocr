/***
 * @pName mi-ocr-common-entity
 * @name Captcha
 * @user HongWei
 * @date 2018/6/18
 * @desc
 */
package org.millions.idea.ocr.web.common.entity;

public class Captcha {
    private String ticket;
    private String channel;
    private byte[] binary;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Captcha(String ticket, String channel, byte[] binary, String token) {

        this.ticket = ticket;
        this.channel = channel;
        this.binary = binary;
        this.token = token;
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
