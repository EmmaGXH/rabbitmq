package com.oracle.rabbit.demo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumers {

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
        //指定消费真即将要消费的队列
        channel.queueDeclare("Emma",true,false,false,null);
        Consumer consumer=new DefaultConsumer(channel){
             /**
             消费者线程在消费当消息之后，需要进行消息处理，写在handleDelivery方法内部
             **/
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        };
        channel.basicConsume("Emma", true, consumer);

    }


}
