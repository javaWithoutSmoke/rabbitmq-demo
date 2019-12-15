package com.javawithoutsmoke;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 */
public class Producer {

    private static final String EXCHANGE_NAME = "Publish-Subscribe";

    public static void main(String[] args) throws IOException, TimeoutException {
        for (int i = 1; i < 7; i++) {
            Connection connection = RabbitMQConnection.getConnection();
            Channel channel = connection.createChannel();
            // 绑定交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);
            channel.basicPublish(EXCHANGE_NAME, "", null, String.valueOf(i).getBytes());
            System.out.println("消息已被发送：" + i);
            channel.close();
            connection.close();
        }
    }
}
