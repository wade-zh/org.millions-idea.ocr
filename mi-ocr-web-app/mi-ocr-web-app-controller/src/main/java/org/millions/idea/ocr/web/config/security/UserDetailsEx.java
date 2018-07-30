/***
 * @pName mi-ocr-web-app
 * @name UserDetailsEx
 * @user HongWei
 * @date 2018/7/28
 * @desc
 */
package org.millions.idea.ocr.web.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetailsEx extends User {
    private String salt;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public UserDetailsEx(String username, String salt, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.salt = salt;
    }
}
