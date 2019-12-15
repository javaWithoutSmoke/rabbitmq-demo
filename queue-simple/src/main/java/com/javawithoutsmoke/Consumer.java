package com.javawithoutsmoke;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 */
public class Consumer {

    private static final String QUEUE_NAME = "HelloWorld";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1、创建连接
        Connection connection = RabbitMQConnection.getConnection();
        // 2、创建通道
        Channel channel = connection.createChannel();
        // 3、设置队列的消费逻辑
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            //接收到一个消息时会使用这个方法，这里进行重写，用来输出接收到的消息
            /*
            参数说明：
            consumerTag：消费者关联的标签
            envelope： 消息包数据
            BasicProperties：消息的额外属性
            body： 消息主体，当前为二进制
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String messageBody = new String(body);
                System.out.println("消费者消费消息："+messageBody);
            }
        };
        // 4、添加监听。队列需要在服务器中先创建好，不然启动会发生 NOT_FOUND - no queue 'HelloWorld'的错误
        // 开启自动确认，表示当消费者一旦接收到消息，队列就会把消息删除。无论消费者之后的处理是否报错
        channel.basicConsume(QUEUE_NAME,true, defaultConsumer);
    }
}
