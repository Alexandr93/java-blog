package com.myspringdemo.blog.repo;

import com.myspringdemo.blog.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RolesRepository extends CrudRepository<Role, Integer> {
    Role findByName(String username);
}
