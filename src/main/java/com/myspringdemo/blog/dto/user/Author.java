package com.myspringdemo.blog.dto.user;

import lombok.Data;

import java.util.Set;

@Data
public class Author {
    private Long id;
    private String username;
    //@JsonIgnore
    private Set<Long> posts;

}
