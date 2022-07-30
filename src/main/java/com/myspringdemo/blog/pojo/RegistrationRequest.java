package com.myspringdemo.blog.pojo;


import com.myspringdemo.blog.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
}
