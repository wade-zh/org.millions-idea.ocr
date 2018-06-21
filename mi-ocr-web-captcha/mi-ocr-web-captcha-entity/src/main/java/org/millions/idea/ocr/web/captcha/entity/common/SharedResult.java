/***
 * @pName mi-ocr-web-captcha
 * @name SharedResult
 * @user HongWei
 * @date 2018/6/21
 * @desc
 */
package org.millions.idea.ocr.web.captcha.entity.common;

public class SharedResult {
    private String ticket;
    private String id;
    private String result;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public SharedResult() {

    }

    public SharedResult(String ticket, String id, String result) {

        this.ticket = ticket;
        this.id = id;
        this.result = result;
    }
}
