/***
 * @pName org.millions-idea.ocr
 * @name RabbitTemplateConfiguration
 * @user HongWei
 * @date 2018/6/8
 * @desc
 */
package org.millions.idea.ocr.web.controller.config;

import org.millions.idea.ocr.web.entity.MultiQueue;
import org.millions.idea.ocr.web.entity.common.RabbitConfig;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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
            connectionFactory.createConnection().createChannel(false)
                    .queueDeclare(multiQueue.getCaptcha(), true, false, false, null);
            BindingBuilder.bind(new Queue(multiQueue.getCaptcha())).to(new DirectExchange(rabbitConfig.getExchange())).with(multiQueue.getCaptcha());
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
            connectionFactory.createConnection().createChannel(false)
                    .queueDeclare(multiQueue.getReport(), true, false, false, null);
            BindingBuilder.bind(new Queue(multiQueue.getReport())).to(new DirectExchange(rabbitConfig.getExchange())).with(multiQueue.getReport());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return multiQueue.getReport();
        }
    }


}
