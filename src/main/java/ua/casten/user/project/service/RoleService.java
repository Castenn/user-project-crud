package ua.casten.user.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.casten.user.project.exception.NullEntityReferenceException;
import ua.casten.user.project.model.Role;
import ua.casten.user.project.model.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role readById(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id parameter less or equals 0.");
        }
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            throw new NullEntityReferenceException("Null project object from DB.");
        }
        return role.get();
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

}
