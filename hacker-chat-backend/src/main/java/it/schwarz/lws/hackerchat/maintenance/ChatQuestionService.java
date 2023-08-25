package it.schwarz.lws.hackerchat.maintenance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatQuestionService {
    private final ChatQuestionRepository chatQuestionRepository;

    public List<ChatQuestion> readChatQuestions() {
        return chatQuestionRepository.findAll();
    }

    public ChatQuestion createChatQuestion(ChatQuestion chatQuestion) {
        return chatQuestionRepository.save(chatQuestion);
    }

    public void deleteChatQuestionById(Long id) {
        chatQuestionRepository.deleteById(id);
    }
}
