package softuni.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.blog.data.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String name);

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);
}
