package it.schwarz.lws.hackerchat.solution;

import it.schwarz.lws.hackerchat.maintenance.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskSolutionRepository extends JpaRepository<TaskSolution, Long> {
    boolean existsByChatUserAndTask(String chatUser, Task task);
}
