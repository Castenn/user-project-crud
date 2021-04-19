package ua.casten.user.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.casten.user.project.exception.NullEntityReferenceException;
import ua.casten.user.project.model.Project;
import ua.casten.user.project.model.User;
import ua.casten.user.project.model.repository.ProjectRepository;
import ua.casten.user.project.model.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final String EXCEPTION_ID_PARAMETER = "Id parameter less or equals 0.";

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public User save(User user) {
        if (user == null) {
            throw new NullEntityReferenceException("Null user object in create.");
        }
        return userRepository.save(user);
    }

    public User readById(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException(EXCEPTION_ID_PARAMETER);
        }
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NullEntityReferenceException("Null user object from DB.");
        }
        return user.get();
    }

    public User readByNickname(String nickname) {
        if (nickname == null || nickname.isEmpty()) {
            throw new IllegalArgumentException("Nickname is null or empty.");
        }
        User user = userRepository.findUserByNickname(nickname);
        if (user == null) {
            throw new NullEntityReferenceException("Null user object from DB.");
        }
        return user;
    }

    public void delete(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException(EXCEPTION_ID_PARAMETER);
        }
        userRepository.deleteById(id);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public boolean userHasProject(Long userId, Long projectId) {
        List<Project> userProjects = projectRepository.findProjectByOwnerId(userId);
        Project project = projectRepository.getOne(projectId);

        return userProjects.contains(project);
    }
}
