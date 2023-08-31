package it.schwarz.lws.hackerchat.chat;

import it.schwarz.lws.hackerchat.maintenance.Task;
import it.schwarz.lws.hackerchat.maintenance.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class ChatService {
    public static final String HACKER = "Malicious Marvin";

    private final SimpMessageSendingOperations messagingTemplate;
    private final TaskService taskService;
    private final TaskScheduler taskScheduler;


    private final List<String> currentChatUsers = new ArrayList<>();

    public void addUser(String user) {
        currentChatUsers.add(user);
    }

    public void removeUser(String user) {
        currentChatUsers.remove(user);
    }

    private void sendChatQuestion(Task task) {
        StringJoiner stringJoiner = new StringJoiner("|");
        stringJoiner.add(task.getQuestion());

        List<String> answers = task.getAnswers();
        for (int i = 0; i < answers.size(); i++) {
            String answer = answers.get(i);
            stringJoiner.add("ABC".charAt(i) + ": " + answer);
        }

        ChatMessage chatMessage = ChatMessage.builder()
                .content(stringJoiner.toString())
                .type(MessageType.QUESTION)
                .userName(HACKER)
                .sendAt(task.getReleaseAt())
                .questionId(task.getId())
                .build();

        messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }

    @Scheduled(fixedRate = 20000)
    public void scheduleTasks() {
        Date currentDate = new Date();

        List<Task> tasks = taskService.readTasksForCurrentDay();

        for (Task task : tasks) {

            LocalDateTime releaseAt = task.getReleaseAt();

            ZoneId zoneId = ZoneId.systemDefault();
            ZonedDateTime zonedDateTime = releaseAt.atZone(zoneId);
            Date plannedDate = Date.from(zonedDateTime.toInstant());

            if (plannedDate.after(currentDate)) {
                task.setDone(true);
                taskService.updateTask(task);
                taskScheduler.schedule(() -> executeTask(task), plannedDate);
            }
        }
    }

    public void executeTask(Task task) {
        sendChatQuestion(task);
    }

    public String getRandomFakeChatAnswer() {
        List<String> chatFakeAnswers = Arrays.asList(
                "Chatten mit Dir sind wie Bugs im Code, nervig und unerwünscht.",
                "Chats mit Dir, so unerwünscht, wie Viren im System.",
                "Deine Chats, ähnlich interessant wie eine Endlosschleife.",
                "Nachrichten von dir sind wie Rauschen im Code-Kosmos. Sie werden ignoriert.",
                "Bitte schalte auf stumm, dein Chat gehört nicht zum Orchester.",
                "Du bist ein Syntaxfehler in meinem digitalen Leben.",
                "Alarm, deine Nachrichten haben Null Nutzen und sind volle Störung.",
                "Keine Zeit, deine Nachricht ist ein Fehler im Algorithmus meiner Geduld.",
                "Deine Nachricht ist so willkommen wie ein Systemabsturz.");

        Random random = new Random();
        return chatFakeAnswers.get(random.nextInt(chatFakeAnswers.size()));
    }

    private String localDateTimeToCronExpression(LocalDateTime localDateTime) {
        int minute = localDateTime.getMinute();
        int hour = localDateTime.getHour();
        int dayOfMonth = localDateTime.getDayOfMonth();
        int month = localDateTime.getMonthValue();

        return String.format("0 %d %d %d %d ?", minute, hour, dayOfMonth, month);
    }
}
