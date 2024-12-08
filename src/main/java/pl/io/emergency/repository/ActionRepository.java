package pl.io.emergency.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.io.emergency.entity.Action;
import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
    List<Action> findByVolunteerId(Long volunteerId);
}
