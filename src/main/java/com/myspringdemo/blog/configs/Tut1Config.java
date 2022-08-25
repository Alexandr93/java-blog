package com.myspringdemo.blog.configs;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;


@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Tut1Config {


    public static final String RECEIVE_QUEUE = "hello";
    public static final String RPC_EXCHANGE = "rpc_exchange";
    public static final String ROUTING_KEY = "rpc_key";

    @Bean
    public TopicExchange rpcExchange() {
        return new TopicExchange(RPC_EXCHANGE);
    }



    @Bean
    public Binding receiveBinding() {
        return BindingBuilder.bind(hello()).to(rpcExchange()).with(ROUTING_KEY);
    }


    @Bean
    public Queue hello() {
        return new Queue(RECEIVE_QUEUE);
    }
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }



}
