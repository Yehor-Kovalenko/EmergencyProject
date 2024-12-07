package pl.io.emergency.resource;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.io.emergency.resource.ResourceEntity;

@Repository
public interface ResourceRepositorium extends JpaRepository<ResourceEntity, Long> {

}
