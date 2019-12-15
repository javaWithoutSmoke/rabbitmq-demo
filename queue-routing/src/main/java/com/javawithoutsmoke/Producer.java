package com.javawithoutsmoke;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 */
public class Producer {

    private static final String EXCHANGE_NAME = "Routing";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQConnection.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
        channel.basicPublish(EXCHANGE_NAME, "key1", null, String.valueOf("key1").getBytes());
        channel.basicPublish(EXCHANGE_NAME, "key2", null, String.valueOf("key2").getBytes());
        System.out.println("消息已发送");
        channel.close();
        connection.close();
    }
}
