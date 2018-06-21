/***
 * @pName mi-ocr-gateway
 * @name HttpResp
 * @user HongWei
 * @date 2018/6/20
 * @desc
 */
package org.millions.idea.ocr.gateway;

import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.json.JsonObjectDecoder;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class HttpResp {
    private int error;
    private String msg;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HttpResp(int error, String msg) {

        this.error = error;
        this.msg = msg;
    }


}
