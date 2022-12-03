package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Publish {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");

        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()) {
            channel.exchangeDeclare("logs", "fanout");
            String message = "teste";
            channel.basicPublish("logs", "",null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("Enviando mensagem");

        }
    }
}
