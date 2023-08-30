package it.schwarz.lws.hackerchat.maintenance;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> readTasks() {
        return taskRepository.findAll();
    }

    public List<Task> readTasksForCurrentDay() {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime startOfDay = currentDate.atStartOfDay();
        LocalDateTime endOfDay = currentDate.atTime(23, 59);

        return taskRepository.findTasksWithAnswersBetweenReleaseDates(startOfDay, endOfDay);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    public Task readTasksById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(EntityNotFoundException::new);
    }
}
