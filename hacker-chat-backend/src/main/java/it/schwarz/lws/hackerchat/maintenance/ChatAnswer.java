package it.schwarz.lws.hackerchat.maintenance;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class ChatAnswer {
    @Id
    @GeneratedValue
    private Long id;
    private String answer;
    private boolean isCorrect;
}
