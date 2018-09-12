package com.example.metaq;

import java.io.Serializable;

public class Account implements Serializable{
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public Account(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
