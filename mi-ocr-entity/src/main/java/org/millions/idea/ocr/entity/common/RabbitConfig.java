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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@ConfigurationProperties(value = "classpath:rabbitmq.properties")
public class RabbitConfig {
    @Value("${rabbitmq.address}")
    private String address;
    @Value("${rabbitmq.username}")
    private String username;
    @Value("${rabbitmq.password}")
    private String password;
    @Value("${rabbitmq.queue}")
    private String queue;
    @Value("${rabbitmq.exchange}")
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
