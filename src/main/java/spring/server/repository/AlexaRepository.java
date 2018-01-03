package spring.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.server.entity.Alexa;

@Repository
public interface AlexaRepository extends JpaRepository<Alexa, Long>{
}
