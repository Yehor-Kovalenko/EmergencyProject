package pl.io.emergency.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.io.emergency.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
