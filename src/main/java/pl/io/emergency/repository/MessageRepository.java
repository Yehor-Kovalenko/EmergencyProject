package pl.io.emergency.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.io.emergency.entity.MessageEntity;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    @Query("select m from MessageEntity m where m.receiverId = :receiverId")
    List<MessageEntity> read(@Param("receiverId") long receiverId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Messages (sender_id, receiver_id, timestamp, title, body) " +
            "VALUES (:#{#message.senderId}, :#{#message.receiverId}, :#{#message.date}, :#{#message.title}, :#{#message.body})",
            nativeQuery = true)
    int insertMessage(@Param("message") MessageEntity message);
}