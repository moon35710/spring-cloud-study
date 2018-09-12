package com.example.metaq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "spring.metaq")
public class MetaqProperties {
    private boolean zkEnable = true;
    private String zkConnect="127.0.0.1:2181";
    private int zkSessionTimeoutMs = 30000;
    private int zkConnectionTimeoutMs = 30000;
    private int zkSyncTimeMs = 5000;
    private String topic;
    private String group = "defalut_group";

    public boolean isZkEnable() {
        return zkEnable;
    }

    public void setZkEnable(boolean zkEnable) {
        this.zkEnable = zkEnable;
    }

    public String getZkConnect() {
        return zkConnect;
    }

    public void setZkConnect(String zkConnect) {
        this.zkConnect = zkConnect;
    }

    public int getZkSessionTimeoutMs() {
        return zkSessionTimeoutMs;
    }

    public void setZkSessionTimeoutMs(int zkSessionTimeoutMs) {
        this.zkSessionTimeoutMs = zkSessionTimeoutMs;
    }

    public int getZkConnectionTimeoutMs() {
        return zkConnectionTimeoutMs;
    }

    public void setZkConnectionTimeoutMs(int zkConnectionTimeoutMs) {
        this.zkConnectionTimeoutMs = zkConnectionTimeoutMs;
    }

    public int getZkSyncTimeMs() {
        return zkSyncTimeMs;
    }

    public void setZkSyncTimeMs(int zkSyncTimeMs) {
        this.zkSyncTimeMs = zkSyncTimeMs;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
