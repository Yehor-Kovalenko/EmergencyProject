package pl.io.emergency.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.io.emergency.entity.TemplateEntity;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity, Long> {
    @Query("select m from TemplateEntity m where m.type = :type and m.language = :language")
    TemplateEntity findByType(@Param("type") String type, @Param("language") String language);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO templates (type, language, title, body) VALUES (:#{#template.type},:#{#template.language}, :#{#template.title}, :#{#template.body})",
            nativeQuery = true)
    void insertTemplate(@Param("template") TemplateEntity template);

}
