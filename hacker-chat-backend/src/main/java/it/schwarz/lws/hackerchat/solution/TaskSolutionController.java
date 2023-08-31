package it.schwarz.lws.hackerchat.solution;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasksolutions")
@RequiredArgsConstructor
public class TaskSolutionController {
    private final TaskSolutionService taskSolutionService;

    @GetMapping
    List<TaskSolution> getTaskSolutions() {
        return taskSolutionService.findAll();
    }

    @GetMapping("/simple")
    List<SimpleTaskSolution> getSimpleTaskSolutions() {
        List<TaskSolution> taskSolutions = taskSolutionService.findAll();
        return taskSolutions.stream()
                .map(ts -> {
                    SimpleTaskSolution simpleTaskSolution = new SimpleTaskSolution();
                    simpleTaskSolution.setChatUser(ts.getChatUser());
                    simpleTaskSolution.setQuestion(ts.getTask().getQuestion());
                    simpleTaskSolution.setCorrectAnswer(ts.getTask().getAnswers().get(ts.getTask().getCorrect()));
                    simpleTaskSolution.setUserAnswer(ts.getTask().getAnswers().get(ts.getAnswerInx()));
                    simpleTaskSolution.setCorrect(ts.getTask().getCorrect().equals(ts.getAnswerInx()));
                    simpleTaskSolution.setRealestAt(ts.getTask().getReleaseAt());
                    simpleTaskSolution.setAnsweredAt(ts.getAnswerAt());
                    return simpleTaskSolution;
                })
                .toList();
    }

    @Getter
    @Setter
    public static class SimpleTaskSolution {
        private String chatUser;
        private String question;
        private String correctAnswer;
        private String userAnswer;
        private Boolean correct;
        private LocalDateTime realestAt;
        private LocalDateTime answeredAt;
    }
}
