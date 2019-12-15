package com.javawithoutsmoke;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 消费者1， header全匹配
 */
public class HeaderConsumer1 {

    private static final String QUEUE_NAME = "HeaderConsumer1";

    public static Map<String, Object> map = new HashMap<>();

    static {
        map.put("ID", 1);
        map.put("Name", "javaWithoutSmoke");
        // x-match  表示header的匹配规则，any 表示任一属性匹配即可，all则需要所有属性都匹配。生产者的channal可以不加这个属性
        map.put("x-match", "all");
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQConnection.getConnection();
        Channel channel = connection.createChannel();
        //通过代码创建queue
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().headers(map).build();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, Producer.EXCHANGE_NAME, "", props.getHeaders());
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String messageBody = new String(body);
                System.out.println("消费者1消费消息："+messageBody);
            }
        };
        channel.basicConsume(QUEUE_NAME,true, defaultConsumer);
    }
}
