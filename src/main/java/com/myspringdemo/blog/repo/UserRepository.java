package com.myspringdemo.blog.repo;

import com.myspringdemo.blog.models.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface UserRepository extends CrudRepository<UserEntity, String> {
    UserEntity findByUsername(String username);
    Iterable<UserEntity> findAllByPostsIsNotNull();
    Iterable<UserEntity> findUserEntityByPostsIsNotNull();
    Iterable<UserEntity> getFirstByPostsIsNotNull();
  //  Long deleteByUsername(String username);
}
