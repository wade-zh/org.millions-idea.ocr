/***
 * @pName mi-ocr-common-entity
 * @name HttpResp
 * @user HongWei
 * @date 2018/6/20
 * @desc
 */
package org.millions.idea.ocr.common.entity.common;

public class HttpResp {
    private Integer error;
    private String msg;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HttpResp(Integer error, String msg) {

        this.error = error;
        this.msg = msg;
    }
}
