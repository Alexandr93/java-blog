/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myspringdemo.blog.controllers;


import com.myspringdemo.blog.pojo.PostModel;
import com.myspringdemo.blog.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;


/**
 * @author admin
 */

@Controller
public class BlogController {


    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {

        this.blogService = blogService;

    }

    @GetMapping("/blog")
    public String blogMain(Model model) {
        List<PostModel> posts = blogService.getAllPosts();
        model.addAttribute("posts", posts);

        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd() {
        return "blog-add";
    }

    @PostMapping("/blog/add")

    public String blogPostAdd(@ModelAttribute PostModel post, Principal principal) {

        blogService.addPost(post, principal.getName());
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
     /*   if(!postRepository.existsById(id))
            return "redirect:/blog";
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(post1 -> {
            post1.setViews(post1.getViews()+1);
            res.add(post1);
            postRepository.save(post1);//сделать позже обновление только просмотров
        });
        */

        PostModel postModel = blogService.postDetails(id);
        model.addAttribute("post", postModel);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") Long id, Model model) {
       /* if(!postRepository.existsById(id))
            return "redirect:/blog";

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);*/

        PostModel post = blogService.postEdit(id);
        model.addAttribute("post", post);

        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String postUpdate(@ModelAttribute PostModel postModel,
                             @PathVariable(value = "id") Long id) {

        blogService.postUpdate(postModel, id);

        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") Long id) {
        blogService.postDelete(id);
        return "redirect:/blog";
    }


}
