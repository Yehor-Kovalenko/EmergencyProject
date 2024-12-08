package pl.io.emergency.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.io.emergency.entity.Action;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

}
