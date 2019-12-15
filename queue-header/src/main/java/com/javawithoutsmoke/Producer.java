package com.javawithoutsmoke;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 */
public class Producer {

    public static final String EXCHANGE_NAME = "Headers";

    public static Map<String, Object> map = new HashMap<>();

    static {
        map.put("ID", 1);
        map.put("Name", "javaWithoutSmoke");
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQConnection.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "headers", true);
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().headers(map).build();
        channel.basicPublish(EXCHANGE_NAME, "java.without.smoke", props, String.valueOf("key1").getBytes());
        System.out.println("消息已发送");
        channel.close();
        connection.close();

    }
}
