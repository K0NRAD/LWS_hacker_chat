package it.schwarz.lws.hackerchat.chat;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String content;
    private String userName;
    private MessageType type;
    private LocalDateTime sendAt;
    private LocalDateTime receivedAt;
    private Long questionId;
}
