package com.javawithoutsmoke;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者subscribe2
 */
public class Consumer2 {

    private static final String QUEUE_NAME = "subscribe2";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQConnection.getConnection();
        Channel channel = connection.createChannel();
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String messageBody = new String(body);
                System.out.println("消费者2消费消息："+messageBody);
            }
        };
        channel.basicConsume(QUEUE_NAME,true, defaultConsumer);
    }
}
