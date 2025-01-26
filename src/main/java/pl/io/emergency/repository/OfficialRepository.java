package pl.io.emergency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.io.emergency.entity.users.Official;

import java.util.List;

@Repository
public interface OfficialRepository extends JpaRepository<Official, Long> {
    List<Official> findAll();

}