package it.schwarz.lws.hackerchat.chat;

import it.schwarz.lws.hackerchat.maintenance.TaskService;
import it.schwarz.lws.hackerchat.solution.TaskSolution;
import it.schwarz.lws.hackerchat.solution.TaskSolutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static it.schwarz.lws.hackerchat.chat.ChatService.HACKER;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final SimpMessageSendingOperations messagingTemplate;
    private final TaskService taskService;
    private final TaskSolutionService taskSolutionService;

    private final Pattern pattern = Pattern.compile("@([^>]+)");

    @MessageMapping("/chat/messages")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        String content = chatMessage.getContent();
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            String atUser = matcher.group(1).trim();
            if (atUser.equals(HACKER)) {
                List<String> tokens = Arrays.stream(content.split(">"))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .toList();
                if (tokens.size() >= 3) {
                    saveTaskSolution(chatMessage, tokens);
                    String topic = "/topic/" + chatMessage.getUserName();
                    messagingTemplate.convertAndSend(topic, chatMessage);
                } else {
                    ChatMessage chatMessageAnswer = ChatMessage.builder()
                            .content(chatService.getRandomFakeChatAnswer())
                            .type(MessageType.CHAT)
                            .userName(HACKER)
                            .sendAt(LocalDateTime.now())
                            .questionId(null)
                            .build();

                    String topic = "/topic/" + chatMessage.getUserName();
                    messagingTemplate.convertAndSend(topic, chatMessage);

                    topic = "/topic/" + chatMessage.getUserName();
                    messagingTemplate.convertAndSend(topic, chatMessageAnswer);
                }
            } else {
                String topic = "/topic/" + atUser;
                messagingTemplate.convertAndSend(topic, chatMessage);

                topic = "/topic/" + chatMessage.getUserName();
                messagingTemplate.convertAndSend(topic, chatMessage);
            }

        } else {
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }

    @MessageMapping("/chat/users")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor accessor) {
        String userName = chatMessage.getUserName();
        chatService.addUser(userName);
        accessor.getSessionAttributes().put("userName", userName);
        return chatMessage;
    }

    private void saveTaskSolution(ChatMessage chatMessage, List<String> tokens) {
        String hacker = tokens.get(0);
        Long taskId = Long.valueOf(tokens.get(1));
        int answerInx = tokens.get(2).toUpperCase().charAt(0) - 'A';
        TaskSolution taskSolution = TaskSolution.builder()
                .chatUser(chatMessage.getUserName())
                .answerInx(answerInx)
                .answerAt(LocalDateTime.now())
                .task(taskService.readTasksById(taskId))
                .build();
        if(!taskSolutionService.exist(taskSolution)) {
            taskSolutionService.create(taskSolution);
        }
    }
}
