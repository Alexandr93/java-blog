package com.myspringdemo.blog.models;

import lombok.*;


import javax.persistence.*;

import javax.validation.constraints.Size;
import java.util.HashSet;

import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    //@NotNull
    @Size(min = 3, message = "Password is too short")
    private String password;




    private boolean enabled =true;
    @ManyToMany//(fetch = FetchType.EAGER)
    @JoinTable(name = "users_has_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    @ToString.Exclude
    private Set<Role> roles;


    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();



    public void addRole(Set<Role> role) {
        this.roles.addAll(role);
    }

    public void deleteRole(Role role) {
        this.roles.remove(role);
    }




}
