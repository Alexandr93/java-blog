package com.myspringdemo.blog.repo;

import com.myspringdemo.blog.models.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends CrudRepository<UserEntity, String> {
   // UserEntity findByUsername(String username);
    Optional<UserEntity> findByUsername(String username);
    Set<UserEntity> findUserEntityByPostsIsNotNull();

  /*  @Query(value = "SELECT users.id, users.username " +
            "FROM users", nativeQuery = true)
    Iterable<UserEntity> getAll();*/

}
