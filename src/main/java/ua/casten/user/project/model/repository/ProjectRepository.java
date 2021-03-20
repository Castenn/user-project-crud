package ua.casten.user.project.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.casten.user.project.model.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(value = "SELECT id, name, description FROM projects " +
            "WHERE id IN (SELECT project_id FROM user_has_project WHERE user_id = ?1)",nativeQuery = true)
    List<Project> findProjectByOwnerId(Long ownerId);

}
