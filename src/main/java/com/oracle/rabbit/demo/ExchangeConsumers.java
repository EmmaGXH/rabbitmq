package com.oracle.rabbit.demo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ExchangeConsumers {

    public static void main(String[] args) throws IOException, TimeoutException, TimeoutException {
        //通过连接工厂基于工厂的设计模式，来生产连接的
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("192.168.110.110");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");

        //生产一个连接，连接是基于tcp协议，amqp协议的连接
        Connection connection=factory.newConnection();
        //创建一个数据管道，将来通过管道来传输消息到rabbitmq的server
        Channel channel=connection.createChannel();

        //声明交换器名称为Emma,交换机类型为direct
        channel.exchangeDeclare("Emma", "direct");
        channel.queueDeclare("Amar", true, false, false, null);
        channel.queueBind("Amar", "Emma", "芝麻开门");
        com.rabbitmq.client.Consumer consumer=new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者线程监听管道，一旦有消息进入交换机，使用管道绑定一个队列，通过routingkey去取交换机中的数据"+new String(body));
            }
        };

        channel.basicConsume("Amar", true,consumer);

    }
}
