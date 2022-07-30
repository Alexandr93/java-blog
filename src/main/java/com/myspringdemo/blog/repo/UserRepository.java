package com.myspringdemo.blog.repo;

import com.myspringdemo.blog.models.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<UserEntity, String> {
    UserEntity findByUsername(String username);
  //  Long deleteByUsername(String username);
}
