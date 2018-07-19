/***
 * @pName security
 * @name HttpResp
 * @user HongWei
 * @date 2018/7/19
 * @desc
 */
package com.example.security;

public class HttpResp {
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HttpResp(int code, String msg) {

        this.code = code;
        this.msg = msg;
    }
}
