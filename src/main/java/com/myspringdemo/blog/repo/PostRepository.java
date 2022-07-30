/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.myspringdemo.blog.repo;

import com.myspringdemo.blog.models.Post;
import com.myspringdemo.blog.models.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *
 * @author admin
 */
public interface PostRepository  extends JpaRepository<Post, Long> {
    Iterable<Post> findPostByAuthor_Id(int author);
    List<Post> findPostByAuthor(UserEntity user, Pageable pageable);
}
