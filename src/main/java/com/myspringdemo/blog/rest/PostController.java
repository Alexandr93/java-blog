package com.myspringdemo.blog.rest;


import com.myspringdemo.blog.dto.blog.PostModel;
import com.myspringdemo.blog.services.BlogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/posts")
public class PostController {
    BlogService blogService;

    @GetMapping
    public ResponseEntity<List<PostModel>> postList() {

        return new ResponseEntity<>(blogService.getAllPosts(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<PostModel> createPost(@RequestBody PostModel postModel, @RequestParam String author) {

        return new ResponseEntity<>(blogService.addPost(postModel, author), HttpStatus.CREATED);
    }


    @PatchMapping("/{id}/edit")
    public ResponseEntity<PostModel> editPost(@RequestBody PostModel postModel, @PathVariable("id") Long id) {


        // PostModel post = blogService.postEdit(id);

        return new ResponseEntity<>(blogService.postUpdate(postModel, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Response> deletePost(@PathVariable Long id) {
        blogService.postDelete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}