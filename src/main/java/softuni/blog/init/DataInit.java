package softuni.blog.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.blog.service.CategoryService;
import softuni.blog.service.RoleService;

@Component
public class DataInit implements CommandLineRunner {

    private final RoleService roleService;
    private final CategoryService categoryService;

    public DataInit(RoleService roleService, CategoryService categoryService) {
        this.roleService = roleService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args){
        this.roleService.initRole();
        this.categoryService.initCategories();
    }
}
