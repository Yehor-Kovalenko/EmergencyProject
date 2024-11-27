package pl.io.emergency.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.io.emergency.entity.ExampleEntity;

import java.util.Optional;

/**
 * Repository interface for managing ExampleEntity entities and performing basic CRUD operations
 * Communicates with the database (persistence layer)
 * Ten interfejs już ma w sobie zaimplementowane podstawowe operacje CRUD
 * Jednakże można zdefiniować dodatkowe operację używając dekoratora @Query i języka SQL
 */
@Repository
public interface ExampleRepository extends JpaRepository<ExampleEntity, Long> {

    @Query("select c from ExampleEntity c where c.name = :name")
    Optional<ExampleEntity> findByName(@Param("name") String name);
}

