package springadvanced.exam.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springadvanced.exam.message.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
}