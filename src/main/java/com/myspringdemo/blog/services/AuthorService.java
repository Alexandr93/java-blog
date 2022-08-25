package com.myspringdemo.blog.services;


import com.myspringdemo.blog.configs.PageSizeProp;
import com.myspringdemo.blog.dto.blog.PostModel;
import com.myspringdemo.blog.dto.user.AuthorListDto;
import com.myspringdemo.blog.models.Post;
import com.myspringdemo.blog.models.UserEntity;
import com.myspringdemo.blog.repo.PostRepository;
import com.myspringdemo.blog.repo.UserRepository;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AuthorService {

    private final UserRepository userRepository;
    private final PageSizeProp pageSizeProp;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Set<AuthorListDto> getAllAuthors() {
        Set<UserEntity> authorUser = userRepository.findUserEntityByPostsIsNotNull();


        return authorUser.stream().map(user -> modelMapper.map(user, AuthorListDto.class)).collect(Collectors.toSet());

    }

    @Transactional
    public List<PostModel> postByAuthor(String username) {
        userRepository.findByUsername(username);
        Pageable pageable = PageRequest.of(0, pageSizeProp.getPageSize());

        List<Post> postModelList = postRepository.findPostByAuthor(userRepository.findByUsername(username).get(), pageable);

        return postModelList.stream().map(post -> modelMapper.map(post, PostModel.class)).collect(Collectors.toList());
    }
}
