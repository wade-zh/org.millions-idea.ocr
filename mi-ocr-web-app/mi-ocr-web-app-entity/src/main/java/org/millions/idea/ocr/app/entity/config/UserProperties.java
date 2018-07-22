/***
 * @pName mi-ocr-web-app
 * @name UserProperties
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */
package org.millions.idea.ocr.app.entity.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "user")
@PropertySource("classpath:application.yml")
public class UserProperties{
    private Security security;

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }
}

