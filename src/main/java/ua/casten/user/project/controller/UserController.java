package ua.casten.user.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.casten.user.project.model.Project;
import ua.casten.user.project.model.Role;
import ua.casten.user.project.model.User;
import ua.casten.user.project.service.ProjectService;
import ua.casten.user.project.service.RoleService;
import ua.casten.user.project.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final ProjectService projectService;

    @Autowired
    public UserController(UserService userService, RoleService roleService, ProjectService projectService) {
        this.userService = userService;
        this.roleService = roleService;
        this.projectService = projectService;
    }

    @GetMapping("/all")
    public String all(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/{id}/info")
    public String info(@PathVariable Long id,
                       Model model) {
        User user = userService.readById(id);
        model.addAttribute("user", user);
        return "user-info";
    }

    @GetMapping("/{id}/read")
    public String read(@PathVariable Long id,
                       Model model) {
        List<Project> projects = projectService.getByUserId(id);
        User user = userService.readById(id);
        model.addAttribute("projects", projects);
        model.addAttribute("user", user);
        return "user-projects";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAll());
        return "user-create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam("roleId") Long roleId) {
        user.setRole(roleService.readById(roleId));
        userService.save(user);
        return "redirect:/users/all";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable Long id,
                         Model model) {
        User user = userService.readById(id);
        List<Role> roles = roleService.getAll();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "user-update";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id,
                         @ModelAttribute("user") User user,
                         @RequestParam("oldPassword") String oldPassword,
                         @RequestParam("roleId") Long roleId) {
        User oldUser = userService.readById(id);
        if (oldUser.getPassword().equals(oldPassword)) {
            user.setRole(roleService.readById(roleId));
            userService.save(user);
        }
        return "redirect:/users/" + id + "/read";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/users/all";
    }

}
