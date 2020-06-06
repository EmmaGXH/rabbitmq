package com.oracle.rabbit.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ExchangeProvider {

    public static void main(String[] args) throws IOException, TimeoutException {
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
        //声明交换器名称为hello,交换机类型为direct
        channel.exchangeDeclare("Emma", "direct");
        String message="love Emma";
        channel.basicPublish("Emma", "芝麻开门", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        channel.close();
        connection.close();
    }

}
