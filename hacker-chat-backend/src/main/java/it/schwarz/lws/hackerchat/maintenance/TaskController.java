package it.schwarz.lws.hackerchat.maintenance;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    ResponseEntity<List<Task>> getTasks() {
        return ResponseEntity.ok(taskService.readTasks());
    }

    @PostMapping
    ResponseEntity<Task> postTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @DeleteMapping
    ResponseEntity<Void> deleteTaskById(@RequestParam Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }
}
