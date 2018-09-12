package com.moon.configserver;

import java.io.Serializable;

public class MessageDto implements Serializable {

    private static final long serialVersionUID = -8612178062449487483L;
    private String id;
    private Object object;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return getId() + "--->" + object;
    }
}
