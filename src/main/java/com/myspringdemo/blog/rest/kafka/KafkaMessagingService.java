package com.myspringdemo.blog.rest.kafka;

import com.myspringdemo.blog.rest.CurrencyRate;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
@AllArgsConstructor
public class KafkaMessagingService{

    private KafkaTemplate<String, CurrencyRate[]> kafkaTemplate;

    public void sendRate(CurrencyRate[] currencyRate){

     ListenableFuture<SendResult<String, CurrencyRate[]>> kafkaFuture = kafkaTemplate.send("test-topic3","test-key", currencyRate);

    }
}
