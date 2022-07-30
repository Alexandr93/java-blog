package com.myspringdemo.blog.services;

import com.myspringdemo.blog.models.Post;
import com.myspringdemo.blog.models.UserEntity;
import com.myspringdemo.blog.pojo.PostModel;
import com.myspringdemo.blog.repo.PostRepository;
import com.myspringdemo.blog.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BlogService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;



    public BlogService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }


    public void addPost(PostModel postModel, String author) {

        UserEntity user = userRepository.findByUsername(author);
        Post post = PostModel.PostModelToPost(postModel);
        post.setAuthor(user);
        postRepository.save(post);


    }

    public List<PostModel> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostModel::PostToPostModel).toList();
    }


    //добавить проверку и ексепшны
    public PostModel postDetails(long id) {

        Optional<Post> post = postRepository.findById(id);
        PostModel postModel;
       // post.get().setViews(post.get().getViews() + 1);
        iterateViews(post);
        postModel = PostModel.PostToPostModel(postRepository.save(post.get()));
        return postModel;

    }

    public PostModel postEdit(long id) {

        Optional<Post> post = postRepository.findById(id);
        PostModel postModel;
        postModel = PostModel.PostToPostModel(postRepository.save(post.get()));
        return postModel;

    }

    public void postUpdate(PostModel postModel, long id){

        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(postModel.getTitle());
        post.setAnons(postModel.getAnons());
        post.setFull_text(postModel.getFull_text());
        postRepository.save(post);
    }

    public void postDelete(Long id){
        Post post = postRepository.findById(id).orElseThrow();

        postRepository.delete(post);
    }

    public void iterateViews(Optional<Post> post){
        post.get().setViews(post.get().getViews() + 1);

    }
}