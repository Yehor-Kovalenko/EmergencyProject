package pl.io.emergency.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.io.emergency.entity.Invitation;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

}
