package ua.casten.user.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.casten.user.project.model.Task;
import ua.casten.user.project.model.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public Task readById(Long id) {
        return taskRepository.getOne(id);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public List<Task> getByProjectId(Long projectId) {
        return taskRepository.findTasksByProjectId(projectId);
    }

}
