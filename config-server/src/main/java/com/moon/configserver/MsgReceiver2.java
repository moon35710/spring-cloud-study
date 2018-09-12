package com.moon.configserver;

import com.moon.configserver.conf.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_B)
public class MsgReceiver2 {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(MessageDto content) {
        logger.info("接收处理队列A当中的消息： {}" ,content);
    }

    public void process2(@Payload Object content) {
        logger.info("接收处理队列A当中的消息： " + content);
    }
}