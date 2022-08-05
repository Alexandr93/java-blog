package com.myspringdemo.blog.services;

import com.myspringdemo.blog.configs.PageSizeProp;
import com.myspringdemo.blog.exception.PostNotFoundException;
import com.myspringdemo.blog.exception.SaveOrUpdateException;
import com.myspringdemo.blog.models.Post;
import com.myspringdemo.blog.models.UserEntity;
import com.myspringdemo.blog.pojo.PostModel;
import com.myspringdemo.blog.pojo.PostModelPage;
import com.myspringdemo.blog.repo.PostRepository;
import com.myspringdemo.blog.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
public class BlogService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private PageSizeProp pageSizeProp;
    @Transactional
    public PostModel addPost(PostModel postModel, String author) {

        UserEntity user = userRepository.findByUsername(author).orElseThrow(() -> new UsernameNotFoundException("Author not found"));
        Post post = PostModel.PostModelToPost(postModel);
        post.setAuthor(user);
        return PostModel.PostToPostModel(postRepository.save(post));


    }
    @Transactional
    public List<PostModel> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostModel::PostToPostModel).toList();
    }
    
    @Transactional
    public List<PostModelPage> getPostsPageable(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Post> posts = postRepository.findAll(pageable);
             return  posts.stream()
                .map(post -> {
                    PostModelPage postModelPage =  PostModelPage.PostToPostModelPage(post);
                    postModelPage.setPageCount(posts.getTotalPages());
                    postModelPage.setItemsCount(posts.getTotalElements());

                    return postModelPage;

                }).toList();
    }



    //добавить проверку и ексепшны
    @Transactional
    public PostModel postDetails(long id) throws SaveOrUpdateException, PostNotFoundException {

        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found"));
        PostModel postModel;
        // post.get().setViews(post.get().getViews() + 1);
        iterateViews(post);
        try {
            postModel = PostModel.PostToPostModel(postRepository.save(post));
            return postModel;
        } catch (Exception e) {
            throw new SaveOrUpdateException("");
            //throw new PostNotFoundException("Post not found");
        }
    }

    @Transactional
    public PostModel postEdit(long id) throws PostNotFoundException {

        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found"));

        return PostModel.PostToPostModel(postRepository.save(post));

    }

    @Transactional
    public PostModel postUpdate(PostModel postModel, long id) {

        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(postModel.getTitle());
        post.setAnons(postModel.getAnons());
        post.setFull_text(postModel.getFull_text());
        return PostModel.PostToPostModel(postRepository.save(post));
    }

    @Transactional
    public void postDelete(Long id) throws PostNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found"));//mb we need to check postexists
        postRepository.delete(post);
    }

    public void iterateViews(Post post) {
        post.setViews(post.getViews() + 1);

    }
}
