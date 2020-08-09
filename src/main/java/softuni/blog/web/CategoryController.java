package softuni.blog.web;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import softuni.blog.data.serviceModel.CategoryAddServiceModel;
import softuni.blog.service.CategoryService;
import softuni.blog.web.apiModel.CategoryResponseModel;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/categories/all")
    public ResponseEntity<List<CategoryResponseModel>> findAllCategories(){
        return this.categoryService.findAllCategories();
    }

    @GetMapping("/categories/add")
    public ResponseEntity<String> addCategory(){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/categories/add")
    public ResponseEntity<String> addCategoryConfirm(@RequestBody CategoryResponseModel categoryResponseModel){
        if(this.categoryService.isExistCategory(categoryResponseModel.getName())){
            return ResponseEntity.status(HttpStatus.IM_USED).body("Category name already exists!");
        }
        CategoryAddServiceModel categoryAddServiceModel = this.modelMapper.map(categoryResponseModel,CategoryAddServiceModel.class);
        return this.categoryService.addCategory(categoryAddServiceModel);
    }
}
