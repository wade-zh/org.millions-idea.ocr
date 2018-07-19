/***
 * @pName security
 * @name BrowserProperties
 * @user HongWei
 * @date 2018/7/19
 * @desc
 */
package com.example.security.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "test.security")
public class BrowserProperties {
    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    private String browser;

}
