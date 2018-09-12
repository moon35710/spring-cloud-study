package com.moon.configserver;

import com.rabbitmq.client.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigServerApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    MsgProducer msgProducer;

    @Test
    public void sendMsg() {
        for (int i = 0; i < 10; i++) {
            msgProducer.sendMsg("test===." + i);
        }
        try {
            Thread.currentThread().join(1000*5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendMsg2() {
        for (int i = 0; i < 10; i++) {
            MessageDto messageDto=new MessageDto();
            messageDto.setId(UUID.randomUUID().toString());
            messageDto.setObject(111);
            msgProducer.sendMsg(messageDto);
        }
        try {
            Thread.currentThread().join(1000*5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void send() throws IOException, TimeoutException {
//        rabbitTemplate.convertAndSend("test", 1);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPort(5672);//注意这里的端口与管理插件的端口不一样
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明一个dirent模式的交换机
        channel.exchangeDeclare("exchange_name", BuiltinExchangeType.DIRECT, true);
        //声明一个非持久化自动删除的队列
        channel.queueDeclare("queue_name", false, false, true, null);//如果该队列不在被使用就删除他 zhe
        //将绑定到改交换机
        channel.queueBind("queue_name", "exchange_name", "route_key");
        //声明一个消息头部
        Map<String, Object> header = new HashMap<>();
        AMQP.BasicProperties.Builder b = new AMQP.BasicProperties.Builder();
        header.put("charset", "utf-8");
        b.headers(header);
        AMQP.BasicProperties bp = b.build();
        //将消息发出去
        channel.basicPublish("exchange_name", "route_key", false, bp, "test3".getBytes());
    }

    @Test
    public void rec() throws IOException, TimeoutException {
//        rabbitTemplate.receive("test", 1);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPort(5672);//注意这里的端口与管理插件的端口不一样
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明一个dirent模式的交换机
        channel.exchangeDeclare("exchange_name", BuiltinExchangeType.DIRECT, true);
        //声明一个非持久化自动删除的队列
        channel.queueDeclare("queue_name", false, false, true, null);//如果该队列不在被使用就删除他 zhe
        //将绑定到改交换机
        channel.queueBind("queue_name", "exchange_name", "route_key");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume("queue_name", true, consumer);
    }

    @Test
    public void contextLoads() {
//        try {
////            Connection connection= connectionFactory.newConnection();
////            Channel channel= connection.createChannel();
////            channel .basicConsume("test",new DefaultConsumer(channel));
////        } catch (IOException e) {
////            e.printStackTrace();
////        } catch (TimeoutException e) {
////            e.printStackTrace();
////        }

    }

}
