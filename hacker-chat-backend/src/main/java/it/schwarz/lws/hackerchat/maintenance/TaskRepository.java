package it.schwarz.lws.hackerchat.maintenance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t JOIN FETCH t.answers WHERE t.releaseAt BETWEEN :start AND :end AND t.done = false" )
    List<Task> findTasksWithAnswersBetweenReleaseDates(@Param("start") LocalDateTime start,
                                                       @Param("end") LocalDateTime end);
}
