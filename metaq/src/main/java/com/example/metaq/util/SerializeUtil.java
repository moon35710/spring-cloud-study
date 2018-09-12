package com.example.metaq.util;

import com.alibaba.fastjson.util.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {
    public static byte[] serialize(Object data) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(data);
            objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(byteArrayOutputStream);
            IOUtils.close(byteArrayOutputStream);
        }
        return byteArrayOutputStream.toByteArray();
    }
    public static Object deserialize(byte[] data) {
        ByteArrayInputStream byteArrayOutputStream = new ByteArrayInputStream(data);
        try {
            ObjectInputStream objectOutputStream = new ObjectInputStream(byteArrayOutputStream);
           return  objectOutputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(byteArrayOutputStream);
            IOUtils.close(byteArrayOutputStream);
        }
        return null;
    }
}
