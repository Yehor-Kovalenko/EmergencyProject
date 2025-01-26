package pl.io.emergency.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.io.emergency.entity.Action;
import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
    List<Action> findByVolunteerId(Long volunteerId);
    @Query("SELECT a.volunteerId FROM Action a WHERE a.catastropheId = :catastropheId AND a.attendance = true")
    List<Long> findVolunteerIdsByCatastropheIdAndAttendanceTrue(@Param("catastropheId") Long catastropheId);
}
