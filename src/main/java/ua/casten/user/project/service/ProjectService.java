package ua.casten.user.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.casten.user.project.exception.NullEntityReferenceException;
import ua.casten.user.project.model.Project;
import ua.casten.user.project.model.Task;
import ua.casten.user.project.model.repository.ProjectRepository;
import ua.casten.user.project.model.repository.TaskRepository;
import ua.casten.user.project.model.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private static final String EXCEPTION_ID_PARAMETER = "Id parameter less or equals 0.";

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public Project save(Project project) {
        if (project == null) {
            throw new NullEntityReferenceException("Null project object in create.");
        }
        return projectRepository.save(project);
    }

    public Project readById(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException(EXCEPTION_ID_PARAMETER);
        }
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new NullEntityReferenceException("Null project object from DB.");
        }
        return project.get();
    }

    public void delete(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException(EXCEPTION_ID_PARAMETER);
        }
        projectRepository.deleteById(id);
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public List<Project> getByUserId(Long userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException(EXCEPTION_ID_PARAMETER);
        }
        return projectRepository.findProjectByOwnerId(userId);
    }

    public boolean projectHasTask(Long projectId, Long taskId) {
        List<Task> projectTasks = taskRepository.findTasksByProjectId(projectId);
        Task task = taskRepository.getOne(taskId);

        return projectTasks.contains(task);
    }

}
