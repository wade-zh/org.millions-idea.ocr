/***
 * @pName security
 * @name ImageCode
 * @user HongWei
 * @date 2018/7/20
 * @desc
 */
package com.example.security.validate;

import org.apache.tomcat.jni.Local;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ImageCode {
    private BufferedImage image;
    private String code;
    private LocalDateTime expire;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

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

    public ImageCode(BufferedImage image, String code, int expire) {
        this.image = image;
        this.code = code;
        this.expire = LocalDateTime.now().plusSeconds(expire);
    }
}
