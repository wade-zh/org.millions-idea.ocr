/***
 * @pName mi-ocr-common-entity
 * @name CaptchaEntity
 * @user HongWei
 * @date 2018/6/13
 * @desc
 */
package org.millions.idea.ocr.common.entity;

public class CaptchaEntity {
    private String ticket;
    private String code;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CaptchaEntity() {

    }

    public CaptchaEntity(String ticket, String code) {

        this.ticket = ticket;
        this.code = code;
    }
}
