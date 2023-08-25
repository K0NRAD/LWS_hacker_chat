package it.schwarz.lws.hackerchat.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final SimpMessageSendingOperations messagingTemplate;

    private final Pattern pattern = Pattern.compile("^@\\S+");

    @MessageMapping("/chat/messages")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        String content = chatMessage.getContent();
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            String topic = "/topic/" + matcher.group(0).substring(1);
            messagingTemplate.convertAndSend(topic, chatMessage);
            topic = "/topic/" + chatMessage.getUserName();
            messagingTemplate.convertAndSend(topic, chatMessage);
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
}
