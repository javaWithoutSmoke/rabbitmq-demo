package com.javawithoutsmoke;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者1
 */
public class TopicConsumer1 {

    private static final String QUEUE_NAME = "TopicConsumer1";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQConnection.getConnection();
        Channel channel = connection.createChannel();
        //通过代码创建queue
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, Producer.EXCHANGE_NAME, "java.without.smoke");
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String messageBody = new String(body);
                System.out.println("消费者1消费消息："+messageBody);
            }
        };
        channel.basicConsume(QUEUE_NAME,true, defaultConsumer);
    }
}
