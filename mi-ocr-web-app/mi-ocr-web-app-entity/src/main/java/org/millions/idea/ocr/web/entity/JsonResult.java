/***
 * @pName mi-ocr-web-app
 * @name JsonResult
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */
package org.millions.idea.ocr.web.entity;

public class JsonResult {
    private Integer error;
    private String message;

    public JsonResult(Integer error, String message) {
        this.error = error;
        this.message = message;
    }

    public Integer getError() {

        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
