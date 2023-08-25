package it.schwarz.lws.hackerchat.maintenance;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class ChatQuestion {
    @Id
    @GeneratedValue
    private Long id;
    private String question;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private List<ChatAnswer> chatAnswers;
    private LocalDateTime sendAt;
}
