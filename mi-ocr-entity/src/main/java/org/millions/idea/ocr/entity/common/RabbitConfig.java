/***
 * @pName org.millions-idea.ocr
 * @name RabbitConfig
 * @user HongWei
 * @date 2018/6/8
 * @desc
 */
package org.millions.idea.ocr.entity.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:application-rabbitmq.properties")
@ConfigurationProperties
public class RabbitConfig {
    @Value("${address}")
    private String address;
    @Value("${username}")
    private String username;
    @Value("${password}")
    private String password;
    @Value("${queue}")
    private String queue;
    @Value("${exchange}")
    private String exchange;

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getQueue() {
        return queue;
    }

    public String getExchange() {
        return exchange;
    }
}
