package ua.casten.user.project.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.casten.user.project.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
