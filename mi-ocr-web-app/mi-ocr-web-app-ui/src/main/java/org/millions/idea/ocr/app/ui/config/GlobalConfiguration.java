/***
 * @pName mi-ocr-web-app
 * @name GlobalConfiguration
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */
package org.millions.idea.ocr.app.ui.config;

import org.millions.idea.ocr.app.entity.config.UserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfiguration {
    @Autowired
    private UserProperties userProperties;

    @Bean
    public UserProperties getUserProperties(){
        return userProperties;
    }
}

