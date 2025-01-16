package pl.io.emergency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.io.emergency.entity.TemplateEntity;


@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity, Long> {
    TemplateEntity findById(long id);
}
