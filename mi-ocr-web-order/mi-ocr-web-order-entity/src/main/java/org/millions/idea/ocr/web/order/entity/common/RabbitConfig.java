/***
 * @pName org.millions-idea.ocr
 * @name RabbitConfig
 * @user HongWei
 * @date 2018/6/8
 * @desc
 */
package org.millions.idea.ocr.web.order.entity.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rabbitmq")
@PropertySource(value = "classpath:/config/rabbitmq.properties")
public class RabbitConfig {
    private String address;
    private String username;
    private String password;
    private String queue;
    private String exchange;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQueue() {
        String[] split = queue.split(",");
        if(split.length > 0) return split[0];
        return queue;
    }

    public String getQueue(int index) {
        String[] split = queue.split(",");
        return split[index];
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}
