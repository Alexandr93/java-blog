/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myspringdemo.blog.controllers;


import com.myspringdemo.blog.exception.PostNotFoundException;
import com.myspringdemo.blog.exception.SaveOrUpdateException;
import com.myspringdemo.blog.pojo.Message;
import com.myspringdemo.blog.pojo.PostModel;
import com.myspringdemo.blog.services.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;


/**
 * @author admin
 */

@Controller
public class BlogController {


    private final BlogService blogService;


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

    public String blogPostAdd(@ModelAttribute PostModel post, Principal principal, HttpSession session) {

        blogService.addPost(post, principal.getName());
        session.setAttribute("message", new Message("Post successfully published", true, "success"));
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) throws PostNotFoundException, SaveOrUpdateException {


        PostModel postModel = blogService.postDetails(id);
        model.addAttribute("post", postModel);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") Long id, Model model) throws PostNotFoundException {

            PostModel post = blogService.postEdit(id);
            model.addAttribute("post", post);

            return "blog-edit";

    }

    @PostMapping("/blog/{id}/edit")
    public String postUpdate(@ModelAttribute PostModel postModel,
                             @PathVariable(value = "id") Long id, HttpSession session, Model model) {

        blogService.postUpdate(postModel, id);

        session.setAttribute("message", new Message("Successfully updated", true, "success"));
        return "redirect:/blog/"+id+"/edit";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") Long id, HttpSession session) throws PostNotFoundException {
        blogService.postDelete(id);
        session.setAttribute("message", new Message("Post deleted", true, "danger"));
        return "redirect:/blog";
    }


}
