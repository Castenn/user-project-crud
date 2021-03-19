package ua.casten.user.project.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.casten.user.project.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
