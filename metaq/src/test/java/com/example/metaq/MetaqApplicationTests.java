package com.example.metaq;

import com.alibaba.fastjson.JSON;
import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.consumer.ConsumerConfig;
import com.taobao.metamorphosis.client.consumer.MessageConsumer;
import com.taobao.metamorphosis.client.consumer.MessageListener;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.client.producer.SendResult;
import com.taobao.metamorphosis.exception.MetaClientException;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Executor;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MetaqApplicationTests {
    @Autowired
    MessagePublisher messagePublisher;

    @Test
    public void contextLoads() {
        messagePublisher.publishMessage("test", new Account(111, "test111"));
    }

    public static void main(String[] args) throws MetaClientException {
        MetaClientConfig metaClientConfig = new MetaClientConfig();
        //设置zookeeper地址
        ZKConfig zkConfig = new ZKConfig();
        zkConfig.zkConnect = "127.0.0.1:2181";
        metaClientConfig.setZkConfig(zkConfig);
        MessageSessionFactory sessionFactory = new MetaMessageSessionFactory(metaClientConfig);
        MessageProducer messageProducer = sessionFactory.createProducer();
        ConsumerConfig consumerConfig = new ConsumerConfig();
        String topic = "ACCOUNT_CHANGED";
//        consumerConfig.setConsumerId("test");
        consumerConfig.setGroup("hsf");
//        consumerConfig.setConsumeFromMaxOffset();
//        consumerConfig.setPartition("1");
        MessageConsumer messageConsumer = sessionFactory.createConsumer(consumerConfig);
        messageConsumer.subscribe(topic, 1024 * 1024, new MessageListener() {
            @Override
            public void recieveMessages(Message message) {
                System.out.println("Receive message " + new String(message.getData()));
            }

            @Override
            public Executor getExecutor() {
                return null;
            }
        });
        messageConsumer.completeSubscribe();
        messageProducer.publish(topic);
        Message message = new Message(topic, "teetdfsa".getBytes());
        try {
            SendResult sendResult = messageProducer.sendMessage(message);
            System.out.println(JSON.toJSONString(sendResult));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        messageProducer.shutdown();
        messageConsumer.shutdown();
    }

}
