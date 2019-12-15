package com.javawithoutsmoke;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者2
 */
public class Consumer2 {

    private static final String QUEUE_NAME = "work-queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1、创建连接
        Connection connection = RabbitMQConnection.getConnection();
        // 2、创建通道
        Channel channel = connection.createChannel();

        // 3、同一时刻服务器只会发送一条消息给消费者
        channel.basicQos(1);

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
                try {
                    // 模拟处理请求耗时较长的情况
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String messageBody = new String(body);
                System.out.println("消费者消费消息："+messageBody);
                // 手动确认，
                // 第一个参数: 默认的消息的唯一标志
                // 第二个参数：是否批量.当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // 4、添加监听,改成手动ack
        channel.basicConsume(QUEUE_NAME,false, defaultConsumer);
    }
}
