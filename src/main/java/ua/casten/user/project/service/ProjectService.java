package ua.casten.user.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.casten.user.project.model.Project;
import ua.casten.user.project.model.repository.ProjectRepository;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project create(Project project) {
        return projectRepository.save(project);
    }

    public Project readById(Long id) {
        return projectRepository.getOne(id);
    }

    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public List<Project> getByUserId(Long userId) {
        return projectRepository.findProjectByOwnerId(userId);
    }

}
