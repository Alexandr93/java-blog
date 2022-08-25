package com.myspringdemo.blog.rest;

import com.myspringdemo.blog.configs.Tut1Config;
import lombok.AllArgsConstructor;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@AllArgsConstructor
@RestController
public class ExchangeController {

    private RestTemplate restTemplate;

    private MessageConverter converter;
    private RabbitTemplate rabbitTemplate;




    //private final Receiver receiver;

    @GetMapping("/api/exchange/")
    public CurrencyRate getCourse(){
        CurrencyRate[] currencyRate =
                restTemplate.getForObject("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchangenew?json", CurrencyRate[].class);//&valcode=USD
      /*  Arrays.stream(currencyRate).forEach(currencyRate1 -> System.out.println(currencyRate1.getTxt()));
        MessageProperties messageProperties= new MessageProperties();
        Message message = rabbitTemplate.getMessageConverter().toMessage(currencyRate[0], null);
        rabbitTemplate.send("hello", message);*/
        rabbitTemplate.convertAndSend(Tut1Config.RPC_EXCHANGE, Tut1Config.ROUTING_KEY, currencyRate[0]);
        System.out.println(currencyRate[0]);

        return currencyRate[0];


    }
}
