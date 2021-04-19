package ua.casten.user.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.casten.user.project.exception.NullEntityReferenceException;
import ua.casten.user.project.model.Priority;
import ua.casten.user.project.model.repository.PriorityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PriorityService {
    private static final String EXCEPTION_ID_PARAMETER = "Id parameter less or equals 0.";

    private final PriorityRepository priorityRepository;

    @Autowired
    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public Priority readById(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException(EXCEPTION_ID_PARAMETER);
        }
        Optional<Priority> priority = priorityRepository.findById(id);
        if (priority.isEmpty()) {
            throw new NullEntityReferenceException("Null priority object from DB.");
        }
        return priority.get();
    }

    public List<Priority> getAll() {
        return priorityRepository.findAll();
    }

}
