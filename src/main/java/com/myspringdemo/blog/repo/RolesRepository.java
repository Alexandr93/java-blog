package com.myspringdemo.blog.repo;

import com.myspringdemo.blog.models.ERole;
import com.myspringdemo.blog.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RolesRepository extends CrudRepository<Role, Long> {
    Role findByName(ERole name);
}
