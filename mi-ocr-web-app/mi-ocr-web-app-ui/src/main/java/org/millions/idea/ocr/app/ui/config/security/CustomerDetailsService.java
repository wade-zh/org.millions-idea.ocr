/***
 * @pName mi-ocr-web-app
 * @name CustomerDetailsService
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */
package org.millions.idea.ocr.app.ui.config.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component("CustomerDetailsService")
public class CustomerDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(username, "123456", AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

    }
}
