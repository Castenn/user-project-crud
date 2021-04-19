package ua.casten.user.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.casten.user.project.exception.NullEntityReferenceException;
import ua.casten.user.project.model.Task;
import ua.casten.user.project.model.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private static final String EXCEPTION_ID_PARAMETER = "Id parameter less or equals 0.";

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task save(Task task) {
        if (task == null) {
            throw new NullEntityReferenceException("Null task object in create.");
        }
        return taskRepository.save(task);
    }

    public Task readById(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException(EXCEPTION_ID_PARAMETER);
        }
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new NullEntityReferenceException("Null task object from DB.");
        }
        return task.get();
    }

    public void delete(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException(EXCEPTION_ID_PARAMETER);
        }
        taskRepository.deleteById(id);
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public List<Task> getByProjectId(Long projectId) {
        if (projectId <= 0) {
            throw new IllegalArgumentException(EXCEPTION_ID_PARAMETER);
        }
        return taskRepository.findTasksByProjectId(projectId);
    }

    public List<Task> getByProjectIdAndCompleted(Long projectId, boolean completed) {
        if (projectId <= 0) {
            throw new IllegalArgumentException(EXCEPTION_ID_PARAMETER);
        }
        return taskRepository.findTasksByProjectIdAndCompleted(projectId, completed);
    }

}
