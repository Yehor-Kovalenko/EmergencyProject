package pl.io.emergency.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.io.emergency.entity.ResourceEntity;

@Repository
public interface ResourceRepositorium extends JpaRepository<ResourceEntity, Long> {

}
