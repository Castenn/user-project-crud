package ua.casten.user.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.casten.user.project.model.Priority;
import ua.casten.user.project.model.repository.PriorityRepository;

import java.util.List;

@Service
public class PriorityService {

    private final PriorityRepository priorityRepository;

    @Autowired
    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public List<Priority> getAll() {
        return priorityRepository.findAll();
    }

}
