package pl.io.emergency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.io.emergency.entity.users.NGO;

import java.util.List;

@Repository
public interface NGORepository extends JpaRepository<NGO, Long> {
    List<NGO> findAll();
    List<NGO> findByNameContainingIgnoreCase(String name);
}