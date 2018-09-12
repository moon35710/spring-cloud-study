package com.example.metaq.handler;

public interface MessageHandler<T> {
    void handle(String bussinessNo, T data);

    String getTopic();
}
