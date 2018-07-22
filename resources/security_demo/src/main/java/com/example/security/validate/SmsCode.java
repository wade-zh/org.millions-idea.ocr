/***
 * @pName security
 * @name ImageCode
 * @user HongWei
 * @date 2018/7/20
 * @desc
 */
package com.example.security.validate;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class SmsCode {
    private String code;
    private LocalDateTime expire;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isExpire() {
        return LocalDateTime.now().isAfter(expire);
    }

    public void setExpire(LocalDateTime expire) {
        this.expire = expire;
    }

    public SmsCode(String code, int expire) {
        this.code = code;
        this.expire = LocalDateTime.now().plusSeconds(expire);
    }
}
