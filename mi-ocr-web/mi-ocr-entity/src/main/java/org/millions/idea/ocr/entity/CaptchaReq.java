/***
 * @pName org.millions-idea.ocr
 * @name CaptchaReq
 * @user HongWei
 * @date 2018/6/8
 * @desc
 */
package org.millions.idea.ocr.entity;

import org.millions.idea.ocr.entity.types.CaptchaType;

public class CaptchaReq {
    private String ticket;
    private CaptchaType type;
    private String img;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public CaptchaType getType() {
        return type;
    }

    public void setType(CaptchaType type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public CaptchaReq(String ticket, CaptchaType type, String img) {

        this.ticket = ticket;
        this.type = type;
        this.img = img;
    }
}
