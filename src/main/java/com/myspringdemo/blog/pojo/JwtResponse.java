package com.myspringdemo.blog.pojo;

import com.myspringdemo.blog.models.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class JwtResponse {
    public JwtResponse(String token, Long id, String username, String email, Set<Role> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;
}
