package softuni.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.blog.data.entity.Category;
import softuni.blog.data.entity.Post;

import softuni.blog.data.entity.User;
import softuni.blog.data.serviceModel.PostAddServiceModel;
import softuni.blog.data.serviceModel.PostEditServiceModel;
import softuni.blog.repository.PostRepository;
import softuni.blog.service.CategoryService;
import softuni.blog.service.PostService;
import softuni.blog.service.UserService;
import softuni.blog.web.apiModel.PostResponseModel;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, UserService userService, CategoryService categoryService, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<List<PostResponseModel>> findAllPosts() {

        if(this.postRepository.count() == 0){
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        List<PostResponseModel> allPosts =  this.postRepository.findAll().stream().map(post -> {
            PostResponseModel postResponseModel = new PostResponseModel();
            this.modelMapper.map(post, postResponseModel);
            postResponseModel.setAuthor(post.getAuthor().getUsername());

            return postResponseModel;
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(allPosts);
    }

    @Override
    public ResponseEntity<String> createPost(PostAddServiceModel postAddServiceModel) {
        Post post = this.modelMapper.map(postAddServiceModel, Post.class);

        this.postRepository.saveAndFlush(post);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<PostResponseModel> getDetails(String title) {
        return findPostByTitle(title);
    }

    @Override
    public boolean existTitle(String title) {
        return this.postRepository.existsPostByTitle(title);
    }


    @Override
    public ResponseEntity<List<PostResponseModel>> findAllPostByAuthor(String author) {

        if(!this.userService.isExistUsername(author)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        User user = this.userService.findByUsername(author);

        List<PostResponseModel> foundPostsByAuthor = this.postRepository.findAllByAuthor(user).stream().map(post -> {
            PostResponseModel postResponseModel = new PostResponseModel();
            this.modelMapper.map(post, postResponseModel);
            postResponseModel.setAuthor(post.getAuthor().getUsername());

            return postResponseModel;
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(foundPostsByAuthor);
    }

    @Override
    public ResponseEntity<List<PostResponseModel>> findAllPostByCategory(String categoryName) {
        if(!this.categoryService.isExistCategory(categoryName)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Category category = this.categoryService.findCategoryByName(categoryName);

        List<PostResponseModel> foundPosts = this.postRepository.findAllByCategory(category).stream().map(post -> {
            PostResponseModel postResponseModel = new PostResponseModel();
            this.modelMapper.map(post, postResponseModel);
            postResponseModel.setAuthor(post.getAuthor().getUsername());

            return postResponseModel;
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(foundPosts);
    }

    @Override
    public ResponseEntity<PostResponseModel> findPostByTitle(String title) {
        if(!existTitle(title)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Post post = this.postRepository.findByTitle(title);
        PostResponseModel postResponseModel = new PostResponseModel();
        this.modelMapper.map(post, postResponseModel);
        postResponseModel.setAuthor(post.getAuthor().getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(postResponseModel);
    }

    @Override
    public ResponseEntity<PostResponseModel> editPost(String title, PostEditServiceModel postEditServiceModel) {
        if(!existTitle(title)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Post post = this.postRepository.findByTitle(title);
        post.setContent(postEditServiceModel.getContent());
        post.setPublished(postEditServiceModel.getPublished());
        post.setSummary(postEditServiceModel.getSummary());
        post.setUrl(postEditServiceModel.getUrl());

        this.postRepository.saveAndFlush(post);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    @Transactional
    public ResponseEntity<String> delete(String titleName) {

        if(!existTitle(titleName)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        this.postRepository.deleteByTitle(titleName);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}