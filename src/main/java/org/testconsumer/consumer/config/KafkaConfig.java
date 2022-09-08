package org.testconsumer.consumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.testconsumer.consumer.DTO.CurrencyRate;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

 /*   public <T> ConsumerFactory<String, T> consumerFactory(Class<T> clazz) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.myspringdemo.blog.rest.CurrencyRate");
       // props.put(JsonDeserializer.TYPE_MAPPINGS, jsonMessageConverter());
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(clazz));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CurrencyRate> currencyRateKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CurrencyRate> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(CurrencyRate.class));
        return factory;
    }*/


    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "app.1");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        // ...
        return props;
    }


    @Bean
    public ConsumerFactory<String, CurrencyRate[]> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps(), new StringDeserializer(), new JsonDeserializer<>(CurrencyRate[].class, false));
    }


    /*  @Bean
      public <T> ConsumerFactory<String, T> consumerFactory(Class<T> clazz) {
          Map<String, Object> props = new HashMap<>();
          props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
          props.put(ConsumerConfig.GROUP_ID_CONFIG, "app.1");
          props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
          props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
          props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

          return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(clazz));
      }*/
    @Bean
    ConcurrentKafkaListenerContainerFactory<String, CurrencyRate[]>
    currencyRateKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CurrencyRate[]> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }


}
