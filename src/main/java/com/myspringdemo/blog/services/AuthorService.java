package com.myspringdemo.blog.services;


import com.myspringdemo.blog.configs.PageSizeProp;
import com.myspringdemo.blog.models.Post;
import com.myspringdemo.blog.models.UserEntity;
import com.myspringdemo.blog.pojo.Author;
import com.myspringdemo.blog.pojo.PostModel;
import com.myspringdemo.blog.repo.PostRepository;
import com.myspringdemo.blog.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AuthorService {

    private final UserRepository userRepository;
    private final PageSizeProp pageSizeProp;
    private final PostRepository postRepository;

    public List<Author> getAllAuthors(){
        Iterable<UserEntity> authorUser = userRepository.findUserEntityByPostsIsNotNull();
        List<Author> authors = new ArrayList<>();

        authorUser.forEach(user ->
                        authors.add(Author.UserEntitiesToAuthor(user))
         );

        return authors.stream().distinct().collect(Collectors.toList());
    }


    public List<PostModel> postByAuthor(String username) {
        userRepository.findByUsername(username);
        Pageable pageable = PageRequest.of(0, pageSizeProp.getPageSize());
        List<Post> postModelList =  postRepository.findPostByAuthor(userRepository.findByUsername(username), pageable);

        return postModelList.stream().map(PostModel::PostToPostModel).collect(Collectors.toList());
    }
}
