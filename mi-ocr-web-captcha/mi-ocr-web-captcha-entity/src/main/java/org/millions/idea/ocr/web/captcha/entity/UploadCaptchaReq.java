/***
 * @pName mi-ocr-web-captcha
 * @name UploadCaptchaReq
 * @user HongWei
 * @date 2018/6/24
 * @desc
 */
package org.millions.idea.ocr.web.captcha.entity;

public class UploadCaptchaReq {
    private String uname;
    private String pwd;
    private String token;
    private byte[] binary;
    private String channel;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

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

    public UploadCaptchaReq(String uname, String pwd, String token, byte[] binary, String channel) {

        this.uname = uname;
        this.pwd = pwd;
        this.token = token;
        this.binary = binary;
        this.channel = channel;
    }
}
