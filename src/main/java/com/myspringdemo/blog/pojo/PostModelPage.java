package com.myspringdemo.blog.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myspringdemo.blog.models.Post;
import com.myspringdemo.blog.models.UserEntity;
import lombok.Data;

@Data
public class PostModelPage {
    private Long id;
    private String title, anons, full_text;
    private int views;
    private int pageCount;
    private Long itemsCount;
   // private int currentPage;
    @JsonIgnore
    private UserEntity author;


    public static PostModelPage PostToPostModelPage(Post post){
        PostModelPage postModel = new PostModelPage();
        postModel.setId(post.getId());
        postModel.setAnons(post.getAnons());
        postModel.setTitle(post.getTitle());
        postModel.setFull_text(post.getFull_text());
        postModel.setAuthor(post.getAuthor());
        postModel.setViews(post.getViews());
        return postModel;
    }


    public static Post PostModelToPost(PostModelPage postModel){
        Post post = new Post();

        post.setTitle(postModel.getTitle());
        post.setAnons(postModel.getAnons());
        post.setFull_text(postModel.getFull_text());
        //post.setAuthor(postModel.getAuthor());
        return post;
    }
}
