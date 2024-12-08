package pl.io.emergency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.io.emergency.entity.HelpRequest;

import java.util.Optional;

@Repository
public interface HelpRequestRepository extends JpaRepository<HelpRequest, Long> {

    Optional<HelpRequest> findByUniqueCode(String uniqueCode);
}
