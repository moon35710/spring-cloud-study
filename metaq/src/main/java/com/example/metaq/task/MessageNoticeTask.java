package com.example.metaq.task;

import com.example.metaq.Account;
import com.example.metaq.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MessageNoticeTask {
    @Autowired
    MessagePublisher messagePublisher;
    @Scheduled(fixedDelay=5000)
    public void fixedDelayJob(){
        messagePublisher.publishMessage("test", new Account(111, UUID.randomUUID().toString()));
    }

}
