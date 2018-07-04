/***
 * @pName org.millions-idea.ocr
 * @name RabbitTemplateConfiguration
 * @user HongWei
 * @date 2018/6/8
 * @desc
 */
package org.millions.idea.ocr.web.captcha.config;

import com.rabbitmq.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.millions.idea.ocr.web.captcha.entity.MultiQueue;
import org.millions.idea.ocr.web.captcha.entity.common.RabbitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitTemplateConfiguration {
    final static Logger logger = LogManager.getLogger(RabbitTemplateConfiguration.class);

    @Autowired
    private RabbitConfig rabbitConfig;

    @Autowired
    private MultiQueue multiQueue;

    @Bean(name="defaultConnectionFactory")
    @Primary
    public ConnectionFactory firstConnectionFactory(){
        return getFactory();
    }

    private ConnectionFactory getFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitConfig.getAddress().split(":")[0]);
        factory.setPort(Integer.valueOf(rabbitConfig.getAddress().split(":")[1]));
        factory.setUsername(rabbitConfig.getUsername());
        factory.setPassword(rabbitConfig.getPassword());
        factory.setVirtualHost("/");
        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(10);
//        connectionFactory.setPublisherConfirms(true);
        return factory;
    }

}
