/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myspringdemo.blog.controllers;


import com.myspringdemo.blog.exception.PostNotFoundException;
import com.myspringdemo.blog.dto.blog.Message;
import com.myspringdemo.blog.dto.blog.PostModel;
import com.myspringdemo.blog.dto.blog.PostModelPage;
import com.myspringdemo.blog.services.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String blogMain(Model model, @RequestParam(value ="page", defaultValue = "1") int page, @RequestParam(value ="size", defaultValue = "3") int size) {
      //  List<PostModel> posts = blogService.getAllPosts();
        System.out.println("Wrong");
        List<PostModelPage> posts = blogService.getPostsPageable(page, size);
        model.addAttribute("currentPage", page);
        model.addAttribute("itemsCount", posts.get(0).getItemsCount());
        model.addAttribute("pagesCount", posts.get(0).getPageCount());
        model.addAttribute("posts", posts);

        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAddForm() {
        return "blog-add";
    }

    @PostMapping("/blog/add")

    public String createPost(@ModelAttribute PostModel post, Principal principal, HttpSession session) {

        blogService.addPost(post, principal.getName());
        session.setAttribute("message", new Message("Post successfully published", true, "success"));
        return "redirect:/blog";

    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {


        PostModel postModel = blogService.postDetails(id);
        model.addAttribute("post", postModel);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") Long id, Model model){

            PostModel postModel = blogService.postDetails(id);
            model.addAttribute("post", postModel);

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
