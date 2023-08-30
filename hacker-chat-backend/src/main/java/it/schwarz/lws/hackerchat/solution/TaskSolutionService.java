package it.schwarz.lws.hackerchat.solution;

import it.schwarz.lws.hackerchat.maintenance.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskSolutionService {

    private final TaskSolutionRepository taskSolutionRepository;
    private final TaskRepository taskRepository;

    public TaskSolutionService(final TaskSolutionRepository taskSolutionRepository,
                               final TaskRepository taskRepository) {
        this.taskSolutionRepository = taskSolutionRepository;
        this.taskRepository = taskRepository;
    }

    public List<TaskSolution> findAll() {
        return taskSolutionRepository.findAll(Sort.by("chatUser"));
    }

    public TaskSolution get(final Long id) {
        return taskSolutionRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Long create(final TaskSolution taskSolution) {
        return taskSolutionRepository.save(taskSolution).getId();
    }

    public void update(final Long id, final TaskSolution taskSolution) {
        taskSolutionRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        taskSolutionRepository.save(taskSolution);
    }

    public void delete(final Long id) {
        taskSolutionRepository.deleteById(id);
    }
}