package com.myspringdemo.blog.repo;

import com.myspringdemo.blog.models.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> {
    UserEntity findByUsername(String username);
}
