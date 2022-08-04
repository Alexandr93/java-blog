package com.myspringdemo.blog.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myspringdemo.blog.models.ERole;
import com.myspringdemo.blog.models.Post;
import com.myspringdemo.blog.models.UserEntity;
import lombok.Data;

import java.util.Set;

@Data
public class PostModel {
    private Long id;
    private String title, anons, full_text;
    private int views;

    @JsonIgnore
    private UserEntity author;


    public static PostModel PostToPostModel(Post post){
        PostModel postModel = new PostModel();
        postModel.setId(post.getId());
        postModel.setAnons(post.getAnons());
        postModel.setTitle(post.getTitle());
        postModel.setFull_text(post.getFull_text());
        postModel.setAuthor(post.getAuthor());
        postModel.setViews(post.getViews());
        return postModel;
    }


    public static Post PostModelToPost(PostModel postModel){
        Post post = new Post();

        post.setTitle(postModel.getTitle());
        post.setAnons(postModel.getAnons());
        post.setFull_text(postModel.getFull_text());
        //post.setAuthor(postModel.getAuthor());
        return post;
    }
}
