package com.example.metaq.config;

import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.consumer.ConsumerConfig;
import com.taobao.metamorphosis.client.consumer.MessageConsumer;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.exception.MetaClientException;
import com.taobao.metamorphosis.utils.ZkUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ConditionalOnClass(value = {MessageSessionFactory.class, MessageProducer.class})
@EnableConfigurationProperties(MetaqProperties.class)
public class MetaqConfig {
    @Autowired
    MetaqProperties metaqProperties;

    @Bean
    public MessageSessionFactory messageSessionFactory() {
        MetaClientConfig metaClientConfig = new MetaClientConfig();
        //设置zookeeper地址
        ZkUtils.ZKConfig zkConfig = new ZkUtils.ZKConfig();
        zkConfig.zkConnect = "127.0.0.1:2181";
        zkConfig.zkConnect = metaqProperties.getZkConnect();
        metaClientConfig.setZkConfig(zkConfig);
        MessageSessionFactory sessionFactory = null;
        try {
            sessionFactory = new MetaMessageSessionFactory(metaClientConfig);
        } catch (MetaClientException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnBean(MessageSessionFactory.class)
    public MessageProducer messageProducer(MessageSessionFactory messageSessionFactory) {
        MessageProducer messageProducer = messageSessionFactory.createProducer();
        if (StringUtils.isNotBlank(metaqProperties.getTopic())) {
            String topics[] = StringUtils.split(metaqProperties.getTopic(), ",");
            for (int i = 0; i < topics.length; i++) {
                messageProducer.publish(topics[i]);
            }
        }
        return messageProducer;
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnBean(MessageSessionFactory.class)
    public MessageConsumer messageConsumer(MessageSessionFactory messageSessionFactory) {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setGroup(metaqProperties.getGroup());
        MessageConsumer messageConsumer = messageSessionFactory.createConsumer(consumerConfig);
        return messageConsumer;
    }

}
