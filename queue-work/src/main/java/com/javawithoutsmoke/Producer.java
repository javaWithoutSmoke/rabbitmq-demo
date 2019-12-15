package com.javawithoutsmoke;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 */
public class Producer {

    // 工作队列
    private static final String QUEUE_NAME = "work-queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        for (int i = 1; i < 7; i++) {
            //1、创建连接
            Connection connection = RabbitMQConnection.getConnection();
            //2、创建通道
            Channel channel = connection.createChannel();
            //3、发送消息
            channel.basicPublish("", QUEUE_NAME, null, String.valueOf(i).getBytes());
            System.out.println("消息已被发送：" + i);
            //4、发送完记得关闭连接
            channel.close();
            connection.close();
        }
    }
}
