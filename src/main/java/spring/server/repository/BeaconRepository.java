package spring.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.server.entity.Beacon;

@Repository
public interface BeaconRepository extends JpaRepository<Beacon, Long> {
}
