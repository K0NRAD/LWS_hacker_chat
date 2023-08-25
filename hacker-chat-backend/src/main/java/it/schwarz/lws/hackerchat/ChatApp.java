package it.schwarz.lws.hackerchat;

import it.schwarz.lws.hackerchat.maintenance.ChatAnswer;
import it.schwarz.lws.hackerchat.maintenance.ChatQuestion;
import it.schwarz.lws.hackerchat.maintenance.ChatQuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class ChatApp {

    public static void main(String[] args) {
        SpringApplication.run(ChatApp.class, args);
    }

    //@Bean
    public CommandLineRunner init(ChatQuestionRepository chatQuestionRepository) {
        return args -> {
            log.info(chatQuestionRepository.save(ChatQuestion.builder()
                    .question("Wieviel Bits hat ein Byte?")
                    .chatAnswers(
                            Arrays.asList(
                                    ChatAnswer.builder().answer("A: 4 Bit").isCorrect(false).build(),
                                    ChatAnswer.builder().answer("B: 8 Bit").isCorrect(true).build(),
                                    ChatAnswer.builder().answer("C: 16 Bit").isCorrect(false).build(),
                                    ChatAnswer.builder().answer("D: 32 Bit").isCorrect(false).build()
                            )
                    )
                    .sendAt(LocalDateTime.of(2023, 6, 29, 16, 0))
                    .build()).toString());

            log.info(chatQuestionRepository.save(ChatQuestion.builder()
                    .question("Was ist eine CPU?")
                    .chatAnswers(
                            Arrays.asList(
                                    ChatAnswer.builder().answer("A: Cool Performance Utility").isCorrect(false).build(),
                                    ChatAnswer.builder().answer("B: Central Processing Unit").isCorrect(true).build(),
                                    ChatAnswer.builder().answer("C: Calendar Post Url").isCorrect(false).build(),
                                    ChatAnswer.builder().answer("D: Cube Port Usage").isCorrect(false).build()
                            )
                    )
                    .sendAt(LocalDateTime.of(2023, 6, 29, 16, 10))
                    .build()
            ).toString());

            chatQuestionRepository.findAll().forEach(chatQuestion -> log.info(chatQuestion.toString()));
        };

    }
}


