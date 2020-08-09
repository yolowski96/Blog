package softuni.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.blog.data.entity.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {
    Category findByName(String categoryName);

    boolean existsCategoryByName(String name);
}
