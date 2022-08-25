package com.myspringdemo.blog.configs;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
/*    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/blog/add").setViewName("blog-add");
    }*/

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }




}
