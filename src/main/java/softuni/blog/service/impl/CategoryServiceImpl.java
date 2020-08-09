package softuni.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import softuni.blog.data.entity.Category;
import softuni.blog.data.serviceModel.CategoryAddServiceModel;
import softuni.blog.repository.CategoryRepository;
import softuni.blog.service.CategoryService;
import softuni.blog.web.apiModel.CategoryResponseModel;

import java.util.List;
import java.util.stream.Collectors;


@Repository
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initCategories() {
        if(this.categoryRepository.count() == 0){
            Category spring = new Category("Spring","Destinations for the spring season");
            Category summer = new Category("Summer","Destinations for the summer season");
            Category autumn = new Category("Autumn","Destinations for the autumn season");
            Category winter = new Category("Winter","Destinations for the winter season");

            this.categoryRepository.saveAndFlush(spring);
            this.categoryRepository.saveAndFlush(summer);
            this.categoryRepository.saveAndFlush(autumn);
            this.categoryRepository.saveAndFlush(winter);
        }
    }

    @Override
    public Category findCategoryByName(String categoryName) {
        return this.categoryRepository.findByName(categoryName);
    }

    @Override
    public boolean isExistCategory(String name) {
        return this.categoryRepository.existsCategoryByName(name);
    }

    @Override
    public ResponseEntity<List<CategoryResponseModel>> findAllCategories() {
        List<CategoryResponseModel> categoryResponseModel =
                this.categoryRepository.findAll().stream().map(c ->
                   this.modelMapper.map(c,CategoryResponseModel.class)
                ).collect(Collectors.toList());

        return ResponseEntity.ok().body(categoryResponseModel);
    }

    @Override
    public ResponseEntity<String> addCategory(CategoryAddServiceModel categoryAddServiceModel) {
        Category category = this.modelMapper.map(categoryAddServiceModel,Category.class);
        this.categoryRepository.saveAndFlush(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
