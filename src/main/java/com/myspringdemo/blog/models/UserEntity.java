package com.myspringdemo.blog.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
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

    private boolean enabled;
    @ManyToMany//(fetch = FetchType.EAGER)
    @JoinTable(name = "users_has_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    @ToString.Exclude
    private Set<Role> roles;




    public void addRole(Set<Role> role) {
        this.roles.addAll(role);
    }

    public void deleteRole(Role role) {
        this.roles.remove(role);
    }



    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
