package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Subscribe {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setPort(5672);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("logs", "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, "logs", "");
        System.out.println("Waiting for messages");
        DeliverCallback deliverCallback = ((s, delivery) -> {
            System.out.println("Received: " + new String(delivery.getBody()));
        });
        channel.basicConsume(queueName, true, deliverCallback, s -> { });

    }


}
