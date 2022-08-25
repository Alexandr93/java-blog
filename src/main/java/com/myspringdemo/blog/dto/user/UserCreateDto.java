package com.myspringdemo.blog.dto.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserCreateDto {


    private String username;
    @Size(min = 3, message = "Password is too short")
    @JsonIgnore
    private String password;
    private boolean enabled;
    // @JsonIgnore
    private Set<Long> roles = new HashSet<>();
}
