/***
 * @pName mi-ocr-web-app
 * @name UserDetailsServiceEx
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */
package org.millions.idea.ocr.web.config.security;

import org.millions.idea.ocr.web.common.utility.json.Address;
import org.millions.idea.ocr.web.entity.agent.UserEntity;
import org.millions.idea.ocr.web.utils.ServletUtil;
import org.millions.idea.ocr.web.biz.IUserService;
import org.millions.idea.ocr.web.common.utility.encrypt.Md5Util;
import org.millions.idea.ocr.web.common.utility.json.JsonUtil;
import org.millions.idea.ocr.web.common.utility.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("UserDetailsServiceEx")
public class UserDetailsServiceEx implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String ip = RequestUtil.getIp(ServletUtil.getRequest());
        UserEntity userEntity = userService.login(username, ip);
        logger.debug(JsonUtil.getJson(userEntity));
        return new UserDetailsEx(userEntity, userEntity.getUserName(), userEntity.getUserName(), userEntity.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
    }


}
