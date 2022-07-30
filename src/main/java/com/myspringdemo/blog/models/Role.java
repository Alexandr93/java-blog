package com.myspringdemo.blog.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private ERole name;
    @ToString.Exclude
    @ManyToMany(mappedBy = "roles")
    List<UserEntity> users;

    public Role(ERole name) {
        this.name = name;
    }
}
