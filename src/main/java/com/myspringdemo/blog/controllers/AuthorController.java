package com.myspringdemo.blog.controllers;


import com.myspringdemo.blog.dto.blog.PostModel;
import com.myspringdemo.blog.dto.user.AuthorListDto;
import com.myspringdemo.blog.services.AuthorService;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @ModelAttribute
    public void getAllUsersList(Model model) {
        Set<AuthorListDto> authorsList = authorService.getAllAuthors();
        model.addAttribute("authorsList", authorsList);

    }

    @GetMapping
    public String authorList() {


        return "authors/author-list";
    }

    @GetMapping("/{username}")
    public String postsByAuthor(@PathVariable String username, Model model) {
        List<PostModel> postModelList = authorService.postByAuthor(username);
        model.addAttribute("postsByAuthor", postModelList);
        return "authors/post-by-author";
    }


}
