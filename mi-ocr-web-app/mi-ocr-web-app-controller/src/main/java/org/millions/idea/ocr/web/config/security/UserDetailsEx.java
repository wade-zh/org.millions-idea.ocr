/***
 * @pName mi-ocr-web-app
 * @name UserDetailsEx
 * @user HongWei
 * @date 2018/7/28
 * @desc
 */
package org.millions.idea.ocr.web.config.security;

import org.millions.idea.ocr.web.entity.agent.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetailsEx extends User {
    private String salt;
    private UserEntity detail;

    public UserEntity getDetail() {
        return detail;
    }

    public void setDetail(UserEntity detail) {
        this.detail = detail;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public UserDetailsEx(UserEntity detail, String username, String salt, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.detail = detail;
        this.salt = salt;
    }
}
