package softuni.blog.service;

import org.springframework.http.ResponseEntity;
import softuni.blog.data.entity.Category;
import softuni.blog.data.serviceModel.CategoryAddServiceModel;
import softuni.blog.web.apiModel.CategoryResponseModel;

import java.util.List;

public interface CategoryService {
    void initCategories();

    Category findCategoryByName(String categoryName);

    boolean isExistCategory(String name);

    ResponseEntity<List<CategoryResponseModel>> findAllCategories();

    ResponseEntity<String> addCategory(CategoryAddServiceModel categoryAddServiceModel);
}
