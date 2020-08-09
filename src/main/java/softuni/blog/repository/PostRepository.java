package softuni.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.blog.data.entity.Category;
import softuni.blog.data.entity.Post;
import softuni.blog.data.entity.User;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post,String> {
    boolean existsPostByTitle(String title);

    List<Post> findAllByAuthor(User author);

    List<Post> findAllByCategory(Category category);

    Post findByTitle(String title);

    void deleteByTitle(String title);
}
