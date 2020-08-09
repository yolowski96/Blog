package softuni.blog.service;

import org.springframework.http.ResponseEntity;
import softuni.blog.data.serviceModel.PostAddServiceModel;
import softuni.blog.data.serviceModel.PostEditServiceModel;
import softuni.blog.web.apiModel.PostResponseModel;

import java.util.List;

public interface PostService {

    ResponseEntity<List<PostResponseModel>> findAllPosts();

    ResponseEntity<String> createPost(PostAddServiceModel postAddServiceModel);

    boolean existTitle(String title);

    ResponseEntity<PostResponseModel> getDetails(String title);

    ResponseEntity<List<PostResponseModel>> findAllPostByAuthor(String author);

    ResponseEntity<List<PostResponseModel>> findAllPostByCategory(String categoryName);

    ResponseEntity<PostResponseModel> findPostByTitle(String title);

    ResponseEntity<PostResponseModel> editPost(String title,PostEditServiceModel postEditServiceModel);

    ResponseEntity<String> delete(String titleName);
}
