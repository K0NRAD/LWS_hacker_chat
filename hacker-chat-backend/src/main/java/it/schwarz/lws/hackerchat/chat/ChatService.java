package it.schwarz.lws.hackerchat.chat;

import it.schwarz.lws.hackerchat.maintenance.ChatQuestion;
import it.schwarz.lws.hackerchat.maintenance.ChatQuestionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class ChatService {
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatQuestionRepository chatQuestionRepository;
    private final TaskScheduler taskScheduler;


    private final List<String> currentUsers = new ArrayList<>();

    public void addUser(String user) {
        currentUsers.add(user);
    }

    public void removeUser(String user) {
        currentUsers.remove(user);
    }


    @PostConstruct
    public void initializeScheduler() {
        LocalDateTime localDateTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<ChatQuestion> chatQuestions = chatQuestionRepository.findChatQuestionBySendAtIsGreaterThanEqual(localDateTime);

        for (ChatQuestion chatQuestion : chatQuestions) {
            Runnable task = () -> {
                sendChatQuestion(chatQuestion);
            };
            LocalDateTime sendAt = chatQuestion.getSendAt();
            taskScheduler.schedule(task, new CronTrigger(localDateTimeToCronExpression(sendAt)));


        }
    }

    private void sendChatQuestion(ChatQuestion chatQuestion) {
        StringJoiner stringJoiner = new StringJoiner("|");
        chatQuestion.getChatAnswers().forEach(chatAnswer -> stringJoiner.add(chatAnswer.getAnswer()));

        ChatMessage chatMessage = ChatMessage.builder()
                .content(stringJoiner.toString())
                .type(MessageType.QUESTION)
                .userName("Hacker")
                .sendAt(chatQuestion.getSendAt())
                .questionId(chatQuestion.getId())
                .build();

        messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }


    private String localDateTimeToCronExpression(LocalDateTime localDateTime) {
        int minute = localDateTime.getMinute();
        int hour = localDateTime.getHour();
        int dayOfMonth = localDateTime.getDayOfMonth();
        int month = localDateTime.getMonthValue();
        //int dayOfWeek = localDateTime.getDayOfWeek().getValue();

        return String.format("0 %d %d %d %d ?", minute, hour, dayOfMonth, month);
    }



}
