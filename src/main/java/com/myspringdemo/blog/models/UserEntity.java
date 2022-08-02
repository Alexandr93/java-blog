package com.myspringdemo.blog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@RequestMapping("users")
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    //@NotNull
    @JsonIgnore
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
    @ToString.Exclude
    private Set<Post> posts = new HashSet<>();



    public void addRole(Set<Role> role) {
        this.roles.addAll(role);
    }

    public void deleteRole(Role role) {
        this.roles.remove(role);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
