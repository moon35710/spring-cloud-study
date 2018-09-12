package com.example.metaq.handler;

import com.alibaba.fastjson.JSON;
import com.example.metaq.config.MetaqProperties;
import com.example.metaq.util.SerializeUtil;
import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.consumer.MessageConsumer;
import com.taobao.metamorphosis.client.consumer.MessageListener;
import com.taobao.metamorphosis.exception.MetaClientException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class DefaultMessageHandler implements InitializingBean, ApplicationContextAware {
    ApplicationContext applicationContext;
    Map<String, MessageHandler> handlerMap = new HashMap<>();
    @Autowired
    MetaqProperties metaqProperties;
    @Autowired
    MessageConsumer messageConsumer;

    @Override
    public void afterPropertiesSet() throws Exception {
        handlerMap = applicationContext.getBeansOfType(MessageHandler.class);
        init();
    }

    public void init() {
        try {
            if (StringUtils.isNotBlank(metaqProperties.getTopic())) {
                String topics[] = StringUtils.split(metaqProperties.getTopic(), ",");
                for (int i = 0; i < topics.length; i++) {
                    messageConsumer.subscribe(topics[i], 1024 * 1024, new MessageListener() {
                        @Override
                        public void recieveMessages(Message message) {
//                            System.out.println("Receive message " + new String(message.getData()));
                            handle(message);
                        }

                        @Override
                        public Executor getExecutor() {
                            return Executors.newCachedThreadPool();
                        }
                    });
                }
            }
            messageConsumer.completeSubscribe();
        } catch (MetaClientException e) {
            e.printStackTrace();
        }
    }

    private void handle(Message message) {
        this.handlerMap.values().forEach(hander -> {
            if (StringUtils.equals(message.getTopic(), hander.getTopic())) {
                hander.handle(message.getAttribute(), SerializeUtil.deserialize(message.getData()));
                return;
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
