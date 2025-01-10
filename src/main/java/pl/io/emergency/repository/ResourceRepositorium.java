package pl.io.emergency.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.io.emergency.entity.ResourceEntity;

import java.util.List;

@Repository
public interface ResourceRepositorium extends JpaRepository<ResourceEntity, Long> {

    // Znajdź wszystkie elementy, gdzie holderId równa się podanej wartości
    List<ResourceEntity> findByHolderId(Long holderId);
    // Znajdź wszystkie elementy, gdzie destinationId równa się podanej wartości
    List<ResourceEntity> findByDestinationId(Long destinationId);
}
