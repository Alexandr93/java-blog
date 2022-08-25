package com.myspringdemo.blog.services;

import com.myspringdemo.blog.configs.PageSizeProp;
import com.myspringdemo.blog.dto.blog.PostModel;
import com.myspringdemo.blog.dto.blog.PostModelPage;
import com.myspringdemo.blog.exception.PostNotFoundException;
import com.myspringdemo.blog.models.Post;
import com.myspringdemo.blog.models.UserEntity;
import com.myspringdemo.blog.rabbitmq.Sender;
import com.myspringdemo.blog.repo.PostRepository;
import com.myspringdemo.blog.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class BlogService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private PageSizeProp pageSizeProp;
    private ModelMapper modelMapper;

    private final Sender tut1Sender;

    @Transactional
    public PostModel addPost(PostModel postModel, String author) {

        UserEntity user = userRepository.findByUsername(author).orElseThrow(() -> new UsernameNotFoundException("Author not found"));
        Post post = modelMapper.map(postModel, Post.class);
        post.setAuthor(user);

        return modelMapper.map(postRepository.save(post), PostModel.class);

    }

    @Transactional
    public List<PostModel> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        tut1Sender.send();
        return posts.stream().map(post -> modelMapper.map(post, PostModel.class)).collect(Collectors.toList());
    }

    @Transactional
    public List<PostModelPage> getPostsPageable(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.stream()
                .map(post -> {

                    PostModelPage postModelPage = modelMapper.map(post, PostModelPage.class);
                    postModelPage.setPageCount(posts.getTotalPages());
                    postModelPage.setItemsCount(posts.getTotalElements());

                    return postModelPage;

                }).toList();
    }


    //добавить проверку и ексепшны
    @Transactional
    public PostModel postDetails(long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found by search"));

        PostModel postModel;

        iterateViews(post);
        modelMapper.typeMap(Post.class, PostModel.class).addMappings(mapping -> mapping.map(source -> source.getAuthor().getUsername(), PostModel::setAuthor));
        postModel = modelMapper.map(post, PostModel.class);




        return postModel;

    }


    @Transactional
    public PostModel postUpdate(PostModel postModel, long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found"));
        post.setTitle(postModel.getTitle());
        post.setAnons(postModel.getAnons());
        post.setFull_text(postModel.getFull_text());
        return modelMapper.map(postRepository.save(post), PostModel.class);

    }

    @Transactional
    public void postDelete(Long id) throws PostNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found"));
        postRepository.delete(post);
    }

    public void iterateViews(Post post) {
        post.setViews(post.getViews() + 1);

    }


}
