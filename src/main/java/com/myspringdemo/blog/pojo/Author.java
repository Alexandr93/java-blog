package com.myspringdemo.blog.pojo;

import com.myspringdemo.blog.models.Post;
import com.myspringdemo.blog.models.UserEntity;
import lombok.Data;

import java.util.Set;

@Data
public class Author {


    private String username;
    private Set<Post> posts;


    public static Author UserEntitiesToAuthor(UserEntity user){
        Author author = new Author();
        author.setUsername(user.getUsername());
        author.setPosts(user.getPosts());
        return author;
    }


}
