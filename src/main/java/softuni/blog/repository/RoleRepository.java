package softuni.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.blog.data.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {

    Role findByAuthority(String auth);
}
