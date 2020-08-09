package softuni.blog.web;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import softuni.blog.data.serviceModel.PostAddServiceModel;
import softuni.blog.data.serviceModel.PostEditServiceModel;
import softuni.blog.service.CategoryService;
import softuni.blog.service.PostService;
import softuni.blog.service.UserService;
import softuni.blog.web.apiModel.PostCreateResponseModel;
import softuni.blog.web.apiModel.PostResponseModel;

import java.util.List;

@RestController
@RequestMapping(value = "/posts",consumes = "application/json",produces = "application/json")
public class PostController {

    private final PostService postService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CategoryService categoryService;

    public PostController(PostService postService, ModelMapper modelMapper, UserService userService, CategoryService categoryService) {
        this.postService = postService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/add")
    public ResponseEntity<String> addPost() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    public ResponseEntity<String> createPost(@RequestBody PostCreateResponseModel postCreateResponseModel) {
        if(this.postService.existTitle(postCreateResponseModel.getTitle())){
            return ResponseEntity.status(HttpStatus.IM_USED).body("Title already exists!");
        }
        if(!this.categoryService.isExistCategory(postCreateResponseModel.getCategory())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not found category!");
        }
        PostAddServiceModel postAddServiceModel = this.modelMapper.map(postCreateResponseModel,PostAddServiceModel.class);
        postAddServiceModel.setAuthor(this.userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        postAddServiceModel.setCategory(this.categoryService.findCategoryByName(postCreateResponseModel.getCategory()));
        return this.postService.createPost(postAddServiceModel);
    }

    @GetMapping("")
    public ResponseEntity<List<PostResponseModel>> listAllPosts() {
        return this.postService.findAllPosts();
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<PostResponseModel>> listAllPostsByAuthor(@PathVariable String username){
        return this.postService.findAllPostByAuthor(username);
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<PostResponseModel>> listAllPostsByCategory(@PathVariable String categoryName){
        return this.postService.findAllPostByCategory(categoryName);
    }

    @GetMapping("/details/{titleName}")
    public ResponseEntity<PostResponseModel> detailPost(@PathVariable String titleName){
        return this.postService.getDetails(titleName);
    }

    @GetMapping("/edit/{titleName}")
    public ResponseEntity<PostResponseModel> editPost(@PathVariable String titleName){
        return this.postService.findPostByTitle(titleName);
    }


    @PostMapping("/edit/{titleName}")
    public ResponseEntity<PostResponseModel> editPostConfirm(@PathVariable String titleName,
                                                             @RequestBody PostCreateResponseModel postCreateResponseModel){
        PostEditServiceModel postEditServiceModel = this.modelMapper.map(postCreateResponseModel,PostEditServiceModel.class);
        System.out.println(postEditServiceModel);
        return this.postService.editPost(titleName,postEditServiceModel);
    }

    @DeleteMapping("/delete/{titleName}")
    public ResponseEntity<String> deletePost(@PathVariable String titleName){
        return this.postService.delete(titleName);
    }
}
