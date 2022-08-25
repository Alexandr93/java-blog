package com.myspringdemo.blog.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Set;

@Data
public class UserModel {
    private Long id;
    private String username;

    private boolean enabled;
    @JsonIgnore
    private Set<Long> roles;


}
