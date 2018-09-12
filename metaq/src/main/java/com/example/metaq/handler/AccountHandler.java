package com.example.metaq.handler;

import com.example.metaq.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AccountHandler implements MessageHandler<Account> {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(String bussinessNo, Account data) {
        logger.info("data:{}", data);
    }

    @Override
    public String getTopic() {
        return "test";
    }
}
