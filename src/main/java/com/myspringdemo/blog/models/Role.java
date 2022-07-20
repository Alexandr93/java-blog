package com.myspringdemo.blog.models;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ToString.Exclude
    @ManyToMany(mappedBy = "roles")
    List<UserEntity> users;


}
