/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myspringdemo.blog.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 *
 * @author admin
 */
@Data
@Entity
public class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title, anons, full_text;
    private int views;


    //разобраться как работает jsonignore
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "user_id")
    private UserEntity author;
    public Post() {
       author = new UserEntity();
       author.setId(2L);//заглушка чтобы посты с рест приходили с автором api
    }

    public Post(String title, String anons, String full_text) {

        this.title = title;
        this.anons = anons;
        this.full_text = full_text;

    }
    

    
    
    
}
