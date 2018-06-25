/***
 * @pName org.millions-idea.ocr
 * @name RabbitTemplateConfiguration
 * @user HongWei
 * @date 2018/6/8
 * @desc
 */
package org.millions.idea.ocr.web.captcha.config;

import com.rabbitmq.client.*;
import org.millions.idea.ocr.web.captcha.biz.IWalletMessageService;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitTemplateConfiguration {

    @Autowired
    private RabbitConfig rabbitConfig;

    @Autowired
    private MultiQueue multiQueue;

    @Autowired
    private IWalletMessageService walletMessageService;


    @Bean(name="defaultConnectionFactory")
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

    @Bean(name="defaultRabbitTemplate")
    @Primary
    public RabbitTemplate defaultRabbitTemplate(
            @Qualifier("defaultConnectionFactory") ConnectionFactory connectionFactory
    ){
        RabbitTemplate captchaRabbitTemplate = new RabbitTemplate(connectionFactory);
        captchaRabbitTemplate.setExchange(rabbitConfig.getExchange());
        return captchaRabbitTemplate;
    }


    @Bean(name="defaultFactory")
    public SimpleRabbitListenerContainerFactory threeFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("defaultConnectionFactory") ConnectionFactory connectionFactory
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public String walletQueue(
            @Qualifier("defaultConnectionFactory") ConnectionFactory connectionFactory
    ) {
        try {
            Channel channel = connectionFactory.createConnection().createChannel(false);
            channel.exchangeDeclare(rabbitConfig.getExchange(), "topic", true,false ,null);
            Map<String, Object> arguments = new HashMap<String, Object>();
            arguments.put("x-dead-letter-exchange", rabbitConfig.getExchange());
            arguments.put("x-message-ttl", 30 * 1000);
            channel.queueDeclare(multiQueue.getWallet(), true, false, false, arguments);
            channel.queueBind(multiQueue.getWallet(), rabbitConfig.getExchange(),multiQueue.getWallet());
            channel.basicQos(1);
            channel.basicConsume(multiQueue.getWallet(), false, new DefaultConsumer(channel) {
                /**
                 * No-op implementation of {@link Consumer#handleDelivery}.
                 *
                 * @param consumerTag
                 * @param envelope
                 * @param properties
                 * @param body
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    try{
                        String message = new String(body, "UTF-8");
                        walletMessageService.onMessage(channel, envelope, message);
                    } catch (Exception e){
                        System.err.println(e.toString());
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return multiQueue.getWallet();
        }
    }


}
