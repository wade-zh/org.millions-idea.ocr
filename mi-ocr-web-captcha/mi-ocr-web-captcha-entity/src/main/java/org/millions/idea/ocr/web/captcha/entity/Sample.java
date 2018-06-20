/***
 * @pName mi-ocr-web
 * @name Sample
 * @user HongWei
 * @date 2018/6/18
 * @desc
 */
package org.millions.idea.ocr.web.captcha.entity;

public class Sample {
    private String captchaId;
    private String captcha;
    private String image;

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Sample(String captchaId, String captcha, String image) {

        this.captchaId = captchaId;
        this.captcha = captcha;
        this.image = image;
    }
}
