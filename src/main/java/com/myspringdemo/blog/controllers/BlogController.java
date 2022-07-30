/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myspringdemo.blog.controllers;


import com.myspringdemo.blog.models.Post;
import com.myspringdemo.blog.models.UserEntity;
import com.myspringdemo.blog.repo.PostRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.myspringdemo.blog.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 *
 * @author admin
 */

@Controller
public class BlogController {

    private PostRepository postRepository;

    private UserRepository userRepository;
    @Autowired
    public BlogController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/blog")
    public String blogMain(Model model){
            Iterable<Post> posts = postRepository.findAll();
            model.addAttribute("posts", posts);
        
            return "blog-main";
    }

     @GetMapping("/blog/add")
    public String blogAdd(Model model){


         return "blog-add";
    }

    @PostMapping("/blog/add")
    /*public String blogPostAdd(@RequestParam String title,
            @RequestParam String anons, @RequestParam String full_text, Model model){
            Post post = new Post(title, anons, full_text);
            */
     public String blogPostAdd(@ModelAttribute Post post, Principal principal){
        UserEntity user = userRepository.findByUsername(principal.getName());

        post.setAuthor(user);
      //  user.getPosts().add(post);
       //    userRepository.save(user);
            //post.setAuthor());
        postRepository.save(post);
        return "redirect:/blog";
    }
    
    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value ="id") long id, Model model){
        if(!postRepository.existsById(id))
            return "redirect:/blog";
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(post1 -> {
            post1.setViews(post1.getViews()+1);
            res.add(post1);
            postRepository.save(post1);//сделать позже обновление только просмотров
        });
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value ="id") Long id, Model model){
        if(!postRepository.existsById(id))
            return "redirect:/blog";
        
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }
    
    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@RequestParam String title,
            @RequestParam String anons, @RequestParam String full_text,@PathVariable(value ="id") Long id, Model model){
       Post post = postRepository.findById(id).orElseThrow();
       post.setTitle(title);
       post.setAnons(anons);
       post.setFull_text(full_text);
       postRepository.save(post);
        return "redirect:/blog";
    }
    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value ="id") Long id, Model model){
       Post post = postRepository.findById(id).orElseThrow();
     
       postRepository.delete(post);
        return "redirect:/blog";
    }
    
    
}
