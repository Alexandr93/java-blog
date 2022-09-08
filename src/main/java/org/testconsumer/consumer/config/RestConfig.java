package org.testconsumer.consumer.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.testconsumer.consumer.DTO.CurrencyRate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RestConfig {


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


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
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }


    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.myspringdemo.blog.rest.CurrencyRate", CurrencyRate.class);
        //idClassMapping.put("thing2", Thing2.class);

        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper());
        return jsonConverter;
    }






}
