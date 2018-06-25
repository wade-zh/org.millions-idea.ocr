/***
 * @pName mi-ocr-web-captcha
 * @name UploadCaptchaReq
 * @user HongWei
 * @date 2018/6/24
 * @desc
 */
package org.millions.idea.ocr.web.captcha.entity;

public class UploadCaptchaReq {
    private String token;
    private byte[] binary;
    private String channel;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public byte[] getBinary() {
        return binary;
    }

    public void setBinary(byte[] binary) {
        this.binary = binary;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public UploadCaptchaReq() {

    }

    public UploadCaptchaReq(String token, byte[] binary, String channel) {
        this.token = token;
        this.binary = binary;
        this.channel = channel;
    }
}
