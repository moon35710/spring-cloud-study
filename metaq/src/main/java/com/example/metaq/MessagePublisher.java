package com.example.metaq;

import com.alibaba.fastjson.JSON;
import com.example.metaq.util.SerializeUtil;
import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.client.producer.SendResult;
import com.taobao.metamorphosis.exception.MetaClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

@Component
public class MessagePublisher {
    @Autowired
    private MessageProducer messageProducer;

    public void publishMessage(String topic, Object data) {

        Message message = new Message(topic, SerializeUtil.serialize(data));
        try {
            messageProducer.publish(topic);
            SendResult sendResult = messageProducer.sendMessage(message);
            System.out.println(JSON.toJSONString(sendResult));
        } catch (InterruptedException | MetaClientException e) {
            e.printStackTrace();
        }
    }

}
