package com.myspringdemo.blog.dto.blog;


import lombok.Data;

@Data
public class PostModelPage {
    private Long id;
    private String title, anons, full_text;
    private int views;
    private int pageCount;
    private Long itemsCount;
    // private int currentPage;
  /*  @JsonIgnore
    private UserModel author;*/


}
