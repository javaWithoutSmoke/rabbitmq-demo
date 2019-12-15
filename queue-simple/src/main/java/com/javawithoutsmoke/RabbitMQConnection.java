package com.javawithoutsmoke;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ 连接类，用于建立与RabbitMQ Server端的连接
 * 官方文档地址:http://previous.rabbitmq.com/v3_5_7/tutorials/tutorial-one-java.html
 *
 */
public class RabbitMQConnection {

    public static String RABBIT_MQ_SERVER_HOST = "192.168.199.240";

    public static int RABBIT_MQ_SERVER_PORT = 5672;

    public static String VIRTUAL_HOST = "/HelloWorld";


    /**
     * 获取RabbitMQ连接
     * @return
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        //1、创建连接
        ConnectionFactory factory = new ConnectionFactory();
        //2、设置主机名
        factory.setHost(RABBIT_MQ_SERVER_HOST);
        //3、设置通讯端口，默认是5672，不专门设置也可以
        //factory.setPort(RABBIT_MQ_SERVER_PORT);
        //4、设置账号和密码
        factory.setUsername("hello");
        factory.setPassword("123456");
        //4、设置Virtual Host
        factory.setVirtualHost(VIRTUAL_HOST);
        //5、创建连接
        return factory.newConnection();
    }
}
