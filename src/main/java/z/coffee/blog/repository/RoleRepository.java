package z.coffee.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import z.coffee.blog.model.Role;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByUserId(Long userId) ;
}
