package pl.io.emergency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.io.emergency.entity.Catastrophe;

@Repository
public interface CatastropheRepository extends JpaRepository<Catastrophe, Long> {
}
