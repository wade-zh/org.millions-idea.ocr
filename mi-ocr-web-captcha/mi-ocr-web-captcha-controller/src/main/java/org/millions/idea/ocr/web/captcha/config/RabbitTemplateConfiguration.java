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
import org.millions.idea.ocr.web.captcha.biz.IWalletMessageService;
import org.millions.idea.ocr.web.captcha.biz.impl.PublishMessageServiceImpl;
import org.millions.idea.ocr.web.captcha.entity.MultiQueue;
import org.millions.idea.ocr.web.captcha.entity.common.RabbitConfig;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class RabbitTemplateConfiguration {
    final static Logger logger = LogManager.getLogger(RabbitTemplateConfiguration.class);

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


    @Bean
    public String walletQueue(
            @Qualifier("defaultConnectionFactory") ConnectionFactory connectionFactory
    ) {
        Channel channel = null;
        try {
            channel = connectionFactory.newConnection().createChannel();
            channel.exchangeDeclare(rabbitConfig.getExchange(), "topic", true,false ,null);
            Map<String, Object> arguments = new HashMap<String, Object>();
            arguments.put("x-dead-letter-exchange", rabbitConfig.getExchange());
            arguments.put("x-message-ttl", 30000);
            channel.queueDeclare(multiQueue.getWallet(), true, false, false, null);
            channel.queueBind(multiQueue.getWallet(), rabbitConfig.getExchange(),multiQueue.getWallet());
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            Channel finalChannel = channel;
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
                    logger.debug("收到" + consumerTag +  "标签消息, 封包长度:" + body.length);

                    try{
                        String message = new String(body, "UTF-8");

                        walletMessageService.onMessage(finalChannel, envelope, message);
                    } catch (Exception e){
                        System.err.println(e.toString());
                    }
                }
            });
        }catch (Exception e){
            System.out.println("queueException: " + e);
        }finally {
            return multiQueue.getWallet();
        }
    }


}
