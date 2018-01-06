package spring.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.server.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
}
