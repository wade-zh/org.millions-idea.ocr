/***
 * @pName org.millions-idea.ocr
 * @name RabbitTemplateConfiguration
 * @user HongWei
 * @date 2018/6/8
 * @desc
 */
package org.millions.idea.ocr.web.captcha.controller.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.millions.idea.ocr.web.captcha.entity.MultiQueue;
import org.millions.idea.ocr.web.captcha.entity.common.RabbitConfig;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitTemplateConfiguration {

    @Autowired
    private RabbitConfig rabbitConfig;

    @Autowired
    private MultiQueue multiQueue;

    @Bean(name="captchaConnectionFactory")
    @Primary
    public ConnectionFactory firstConnectionFactory(){
        return getFactory();
    }

    private ConnectionFactory getFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(rabbitConfig.getAddress());
        connectionFactory.setUsername(rabbitConfig.getUsername());
        connectionFactory.setPassword(rabbitConfig.getPassword());
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }


    @Bean(name="reportConnectionFactory")
    public ConnectionFactory reportConnectionFactory(){
        return getFactory();
    }



    @Bean(name="captchaRabbitTemplate")
    @Primary
    public RabbitTemplate captchaRabbitTemplate(
            @Qualifier("captchaConnectionFactory") ConnectionFactory connectionFactory
    ){
        RabbitTemplate captchaRabbitTemplate = new RabbitTemplate(connectionFactory);
        captchaRabbitTemplate.setExchange(rabbitConfig.getExchange());
        return captchaRabbitTemplate;
    }

    @Bean(name="reportRabbitTemplate")
    public RabbitTemplate reportRabbitTemplate(
            @Qualifier("reportConnectionFactory") ConnectionFactory connectionFactory
    ){
        RabbitTemplate reportRabbitTemplate = new RabbitTemplate(connectionFactory);
        reportRabbitTemplate.setExchange(rabbitConfig.getExchange());
        return reportRabbitTemplate;
    }


    @Bean(name="captchaFactory")
    public SimpleRabbitListenerContainerFactory firstFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("captchaConnectionFactory") ConnectionFactory connectionFactory
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean(name="reportFactory")
    public SimpleRabbitListenerContainerFactory secondFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("reportConnectionFactory") ConnectionFactory connectionFactory
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }


    @Bean
    public String captchaQueue(
            @Qualifier("captchaConnectionFactory") ConnectionFactory connectionFactory
    ) {
        try {
            Channel channel = connectionFactory.createConnection().createChannel(false);
            channel.exchangeDeclare(rabbitConfig.getExchange(), "topic", true,false ,null);
            channel.queueDeclare(multiQueue.getCaptcha(), true, false, false, null);
            channel.queueBind(multiQueue.getCaptcha(), rabbitConfig.getExchange(),multiQueue.getCaptcha());
            channel.basicConsume(multiQueue.getCaptcha(), false, null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return multiQueue.getCaptcha();
        }
    }


    @Bean
    public String reportQueue(
            @Qualifier("reportConnectionFactory") ConnectionFactory connectionFactory
    ) {
        try {
            Channel channel = connectionFactory.createConnection().createChannel(false);
            channel.exchangeDeclare(rabbitConfig.getExchange(), "topic", false,false ,null);
            channel.queueDeclare(multiQueue.getReport(), false, true, false, null);
            channel.queueBind(multiQueue.getReport(), rabbitConfig.getExchange(),multiQueue.getReport());
            channel.basicConsume(multiQueue.getReport(), false, null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return multiQueue.getReport();
        }
    }


}
