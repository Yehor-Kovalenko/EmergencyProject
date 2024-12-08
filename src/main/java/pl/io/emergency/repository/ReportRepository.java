package pl.io.emergency.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.io.emergency.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

//    @Query("select c from ExampleEntity c where c.name = :name")
//    Optional<ExampleEntity> findByName(@Param("name") String name);
}

