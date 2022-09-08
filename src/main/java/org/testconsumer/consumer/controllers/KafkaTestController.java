package org.testconsumer.consumer.controllers;



import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.testconsumer.consumer.DTO.CurrencyRate;

@EnableKafka
@RestController
public class KafkaTestController {


    @KafkaListener(topics = "test-topic3", containerFactory = "currencyRateKafkaListenerContainerFactory", groupId = "app.1")
    @GetMapping("/kafka")
    public String getKafkaMessage(ConsumerRecord<String, CurrencyRate[]> record){

        //System.out.println(currencyRate);

        System.out.println(record.offset());
        System.out.println(record.topic());
        for(CurrencyRate r: record.value()){
            System.out.println(r);
        }
        //System.out.println(record.value().getTxt());

        return "hello";
    }

}
