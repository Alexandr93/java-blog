package com.myspringdemo.blog.dto.blog;



import lombok.Data;



@Data
public class PostModel {
    private Long id;
    private String title, anons, full_text;
    private int views;

   // @JsonIgnore
    private String author;

}
