package ua.casten.user.project.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.casten.user.project.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
