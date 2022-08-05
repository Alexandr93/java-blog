package com.myspringdemo.blog.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "author.orders")
public class PageSizeProp {
    private int pageSize = 4;

}
