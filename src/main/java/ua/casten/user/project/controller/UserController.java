package ua.casten.user.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.casten.user.project.model.User;
import ua.casten.user.project.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getAll(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("{id}/read")
    public String getUserInfo(@PathVariable Long id, Model model) {
        User user = userService.readById(id);
        model.addAttribute("user", user);
        return "user-read";
    }

}
