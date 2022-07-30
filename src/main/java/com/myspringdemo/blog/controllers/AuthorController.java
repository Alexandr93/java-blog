package com.myspringdemo.blog.controllers;

import com.myspringdemo.blog.configs.PageSizeProp;
import com.myspringdemo.blog.models.UserEntity;
import com.myspringdemo.blog.repo.PostRepository;
import com.myspringdemo.blog.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors")
public class AuthorController {


    UserRepository userRepository;

    PostRepository postRepository;
    PageSizeProp pageSizeProp;

    @Autowired
    public AuthorController(UserRepository userRepository, PostRepository postRepository, PageSizeProp pageSizeProp) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.pageSizeProp = pageSizeProp;
    }

    public AuthorController() {
    }

    @ModelAttribute
    public void getAllUsersList(Model model) {
        Iterable<UserEntity> authorsList = userRepository.findAll();
        model.addAttribute("authorsList", authorsList);
    }


    public AuthorController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping
    public String authorList() {


        return "authors/author-list";
    }

    @GetMapping("/{username}")
    public String postsByAuthor(@PathVariable String username, Model model) {
        // Iterable<Post> postsByAuthor = postRepository.findPostByAuthor(author.get);
        // author.getPosts().forEach(post -> System.out.println(post.getTitle()));

        Pageable pageable = PageRequest.of(0, pageSizeProp.getPageSize());
        UserEntity author = userRepository.findByUsername(username);

        model.addAttribute("author", author);

        model.addAttribute("postsByAuthor", postRepository.findPostByAuthor(author, pageable));
        // return "redirect:/blog";
        return "authors/post-by-author";
    }


}
