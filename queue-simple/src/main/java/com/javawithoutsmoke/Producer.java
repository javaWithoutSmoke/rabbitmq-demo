package com.javawithoutsmoke;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 */
public class Producer {

    private static final String QUEUE_NAME = "HelloWorld";

    public static void main(String[] args) throws IOException, TimeoutException {

        while (true) {
//            System.out.println("请输入消息：");
            Scanner scanner = new Scanner(System.in);
            //1、创建连接
            Connection connection = RabbitMQConnection.getConnection();
            //2、创建通道
            Channel channel = connection.createChannel();
            //3、发送消息,这里使用Scanner通过控制台输入的内容来作为消息
            //nextLine() 以回车结束当前的输入，会接收空格
            String message = scanner.nextLine();
            /*
            参数说明：
            exchange：当期尚未指定exchange,又不能为null，这里用空字符串表示为一个默认的exchange或者匿名的exchange
            routingKey： 就是队列名称
            props：消息的额外属性
            body： 消息主体
             */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("消息已被发送：" + message);
            //发送完记得关闭连接
            channel.close();
            connection.close();
        }
    }
}
