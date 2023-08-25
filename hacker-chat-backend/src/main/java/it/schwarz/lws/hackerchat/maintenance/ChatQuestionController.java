package it.schwarz.lws.hackerchat.maintenance;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class ChatQuestionController {
    private final ChatQuestionService chatQuestionService;

    @GetMapping
    ResponseEntity<List<ChatQuestion>> getChatQuestions() {
        return ResponseEntity.ok(chatQuestionService.readChatQuestions());
    }

    @PostMapping
    ResponseEntity<ChatQuestion> postChatQuestion(@RequestBody ChatQuestion chatQuestion) {
        return ResponseEntity.ok(chatQuestionService.createChatQuestion(chatQuestion));
    }

    @DeleteMapping
    ResponseEntity<Void> deleteChatQuestionById(@RequestParam Long id) {
        chatQuestionService.deleteChatQuestionById(id);
        return ResponseEntity.noContent().build();
    }
}
