/***
 * @pName org.millions-idea.ocr
 * @name RabbitTemplateConfiguration
 * @user HongWei
 * @date 2018/6/8
 * @desc
 */
package org.millions.idea.ocr.web.order.config;

import com.rabbitmq.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.millions.idea.ocr.web.common.utility.encrypt.Md5Util;
import org.millions.idea.ocr.web.order.biz.listener.IPayMessageQueueListener;
import org.millions.idea.ocr.web.order.entity.common.MultiQueue;
import org.millions.idea.ocr.web.order.entity.common.RabbitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Configuration
public class RabbitTemplateConfiguration {
    final static Logger logger = LogManager.getLogger(RabbitTemplateConfiguration.class);
    static Map<String, Integer> tags = new HashMap<>();

    @Autowired
    private RabbitConfig rabbitConfig;

    @Autowired
    private MultiQueue multiQueue;

    @Autowired
    @Qualifier("ReduceMessageQueueListener")
    private IPayMessageQueueListener reduceMessageQueueListenerImpl;


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
    public String reduceQueue(
            @Qualifier("defaultConnectionFactory") ConnectionFactory connectionFactory
    ) {
        try {
            Channel channel = bindChannel(connectionFactory, multiQueue.getOrderPay());
            channel.basicConsume(multiQueue.getOrderPay(), false,new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String hash = null;
                    try{
                        String message = new String(body, "UTF-8");
                        hash = Md5Util.getMd5(message);
                        reduceMessageQueueListenerImpl.onMessage(channel, envelope, message);
                    } catch (Exception e){
                        logger.error("onMessage异常:" + e.toString());
                        if (e.getMessage().equalsIgnoreCase("A query was run and no Result Maps were found for the Mapped Statement")
                                || e.getMessage().equalsIgnoreCase("余额不足")
                                || isOutMaxRetryCount(hash)) {
                            errorPrint("超过重试上限，删除此消息:" + hash);
                            channel.basicAck(envelope.getDeliveryTag(), false);
                            return;
                        }
                        channel.basicReject(envelope.getDeliveryTag(), true);
                        errorPrint("拒绝此条消息，重发回队列中:" + hash);
                    }
                }
            });
        }catch (Exception e){
            logger.error("定义并绑定队列时抛出异常:" + e.toString());
            errorPrint("定义并绑定队列时抛出异常");
        }finally {
            return multiQueue.getOrderPay();
        }
    }


    @Bean
    public String reportQueue(
            @Qualifier("defaultConnectionFactory") ConnectionFactory connectionFactory
    ) {
        try {
            Channel channel = bindChannel(connectionFactory, multiQueue.getReport());
            channel.basicConsume(multiQueue.getReport(), false,new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String hash = null;
                    try{
                        String message = new String(body, "UTF-8");
                        hash = Md5Util.getMd5(message);
                        reduceMessageQueueListenerImpl.onMessage(channel, envelope, message);
                    } catch (Exception e){
                        logger.error("onMessage异常:" + e.toString());
                        if (e.getMessage().equalsIgnoreCase("A query was run and no Result Maps were found for the Mapped Statement")
                                || e.getMessage().equalsIgnoreCase("余额不足")
                                || isOutMaxRetryCount(hash)) {
                            errorPrint("超过重试上限，删除此消息:" + hash);
                            channel.basicAck(envelope.getDeliveryTag(), false);
                            return;
                        }
                        channel.basicReject(envelope.getDeliveryTag(), true);
                        errorPrint("拒绝此条消息，重发回队列中:" + hash);
                    }
                }
            });
        }catch (Exception e){
            logger.error("定义并绑定队列时抛出异常:" + e.toString());
            errorPrint("定义并绑定队列时抛出异常");
        }finally {
            return multiQueue.getOrderPay();
        }
    }

    private void errorPrint(String buff){
        System.err.println(buff);
    }

    private Channel bindChannel(ConnectionFactory connectionFactory, String queueName) throws IOException, TimeoutException {
        Channel channel  = connectionFactory.newConnection().createChannel();
        channel.exchangeDeclare(rabbitConfig.getExchange(), "topic", true,false ,null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, rabbitConfig.getExchange(), queueName);
        channel.basicQos(1);
        return channel;
    }


    private boolean isOutMaxRetryCount(String hash){
        Integer num = tags.get(hash);
        if(num == null) num = 0;
        if(num >= 3) {
            tags.remove(hash);
            return true;
        }
        tags.put(hash, num+1);
        return false;
    }
}
