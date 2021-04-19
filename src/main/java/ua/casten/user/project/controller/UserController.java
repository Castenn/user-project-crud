package ua.casten.user.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.casten.user.project.model.Project;
import ua.casten.user.project.model.Role;
import ua.casten.user.project.model.User;
import ua.casten.user.project.service.RoleService;
import ua.casten.user.project.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService,
                          RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public String all(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/{userId}")
    public String info(@PathVariable Long userId,
                       Model model) {
        User user = userService.readById(userId);
        model.addAttribute("user", user);
        return "user-info";
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
        return "redirect:/user/all";
    }

    @GetMapping("/{userId}/update")
    public String update(@PathVariable Long userId,
                         Model model) {
        User user = userService.readById(userId);
        List<Role> roles = roleService.getAll();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "user-update";
    }

    @PostMapping("/{userId}/update")
    public String update(@PathVariable Long userId,
                         @ModelAttribute("user") User user,
                         @RequestParam("oldPassword") String oldPassword,
                         @RequestParam("roleId") Long roleId) {
        User oldUser = userService.readById(userId);
        List<Project> userProjects = oldUser.getProjects();
        if (oldUser.getPassword().equals(oldPassword)) {
            user.setRole(roleService.readById(roleId));
            user.setProjects(userProjects);
            userService.save(user);
        }
        return "redirect:/user/" + userId;
    }

    @GetMapping("/{userId}/delete")
    public String delete(@PathVariable Long userId) {
        userService.delete(userId);
        return "redirect:/user/all";
    }

}
