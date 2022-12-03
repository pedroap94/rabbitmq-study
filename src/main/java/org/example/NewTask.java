package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class NewTask {
    private static final String NOME_FILA = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setPassword("admin");
        connectionFactory.setUsername("admin");

        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()){

            //a mensagem irá permanecer caso o rabbit seja reiniciado
            //contudo, não é garantia 100% de que a mensagem não irá ser perdida. O que acontece, é que o rabbit salva no HD a mensagem.
            boolean durable = true;
            channel.queueDeclare("task_queue", durable, false, false, null);
            String message = String.join(" ", args);
            channel.basicPublish("", NOME_FILA, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }



}
