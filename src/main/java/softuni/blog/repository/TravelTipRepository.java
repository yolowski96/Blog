package softuni.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.blog.data.entity.TravelTip;


@Repository
public interface TravelTipRepository extends JpaRepository<TravelTip,String> {

    boolean existsByTitle(String title);
}
