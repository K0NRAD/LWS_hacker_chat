package it.schwarz.lws.hackerchat.maintenance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatQuestionRepository extends JpaRepository<ChatQuestion, Long> {

    public List<ChatQuestion> findChatQuestionBySendAtBetween(LocalDateTime from, LocalDateTime until);

    public List<ChatQuestion> findChatQuestionBySendAtIsGreaterThanEqual(LocalDateTime localDateTime);
}
