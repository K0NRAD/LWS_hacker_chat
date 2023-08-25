package it.schwarz.lws.hackerchat.configuration;

import it.schwarz.lws.hackerchat.chat.ChatMessage;
import it.schwarz.lws.hackerchat.chat.ChatService;
import it.schwarz.lws.hackerchat.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatService chatService;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String userName = (String) accessor.getSessionAttributes().get("userName");
        if (userName != null) {
            chatService.removeUser(userName);
            log.info("User disconnected: {}", userName);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .content(userName + " leave the chat")
                    .userName(userName)
                    .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
