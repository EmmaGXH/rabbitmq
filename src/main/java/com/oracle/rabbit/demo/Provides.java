package com.oracle.rabbit.demo;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provides {

    public static void main(String args[]) throws IOException, TimeoutException {

        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("192.168.110.110");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        //创建一个连接
        Connection connection=factory.newConnection();
        //创建数据管道，用来传输消息到rabbitmq上的server上
        Channel channel=connection.createChannel();
       //创建队列,将消息传输到队列上，提供给消费者
       channel.queueDeclare("Emma", true, false, false, null);
        String message="love Amar";
        //生产者发布消息到指定队列
        channel.basicPublish("", "Emma", MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes() );
        channel.close();
        connection.close();


    }

}
