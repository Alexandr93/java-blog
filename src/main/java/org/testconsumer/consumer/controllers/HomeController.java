package org.testconsumer.consumer.controllers;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.testconsumer.consumer.DTO.CurrencyRate;


@RestController
public class HomeController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageConverter messageConverter;

    //private final Receiver receiver;

    @GetMapping("/")
    public String getCourse() {
        String currencyRate =
                restTemplate.getForObject("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchangenew?json&valcode=USD", String.class);


        return currencyRate;

       /* ResponseEntity<CurrencyRate[]> responseEntity1 =
                restTemplate.getForEntity("https://bank.gov.ua/NBU_Exchange/exchange?json", CurrencyRate[].class);*/

        //ResponseEntity<Items> data = restTemplate.getForEntity("https://bank.gov.ua/NBU_Exchange/exchange?json", Items.class);
        /*List<CurrencyRate> currencyRate = restTemplate.getForObject(
                "https://bank.gov.ua/NBU_Exchange/exchange?json", CurrencyRate.class);*/

        // return responseEntity;
    }

    @GetMapping(path = "/get", produces = "application/json")

    public CurrencyRate get() {


        //System.out.println(message);
       // return new CurrencyRate();
        //return message != null ? (CurrencyRate) messageConverter.fromMessage(message) : null;


        return (CurrencyRate) rabbitTemplate.receiveAndConvert("hello");
       // return (CurrencyRate) rabbitTemplate.receiveAndConvert("hello");

       /* Object ob =         rabbitTemplate.receive("hello").getBody();

        return ob;*/
    }
}
