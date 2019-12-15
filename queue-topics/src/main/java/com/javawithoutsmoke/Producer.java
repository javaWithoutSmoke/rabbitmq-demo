package com.javawithoutsmoke;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 */
public class Producer {

    public static final String EXCHANGE_NAME = "Topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQConnection.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);
        channel.basicPublish(EXCHANGE_NAME, "java.without.smoke", null, String.valueOf("key1").getBytes());
        System.out.println("消息已发送");
        channel.close();
        connection.close();
    }
}
