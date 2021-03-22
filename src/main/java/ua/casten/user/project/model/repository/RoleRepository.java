package ua.casten.user.project.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.casten.user.project.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
