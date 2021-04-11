package ua.casten.user.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.casten.user.project.model.Project;
import ua.casten.user.project.model.User;
import ua.casten.user.project.service.ProjectService;
import ua.casten.user.project.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user/{userId}/project")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    @Autowired
    public ProjectController(ProjectService projectService,
                             UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public String read(@PathVariable("userId") Long userId,
                       Model model) {
        List<Project> projects = projectService.getByUserId(userId);
        User user = userService.readById(userId);
        model.addAttribute("projects", projects);
        model.addAttribute("user", user);
        return "user-projects";
    }

    @GetMapping("/create")
    public String create(@PathVariable("userId") Long userId,
                         Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("userId", userId);
        return "project-create";
    }

    @PostMapping("/create")
    public String create(@PathVariable("userId") Long userId,
                         @ModelAttribute("project") Project project) {
        List<User> ownerList = new ArrayList<>();
        ownerList.add(userService.readById(userId));
        project.setOwners(ownerList);
        projectService.create(project);
        return "redirect:/user/" + userId + "/project/all";
    }

    @GetMapping("/{projectId}")
    public String info(@PathVariable("projectId") Long projectId,
                       @PathVariable("userId") Long userId,
                       Model model) {
        if (!projectService.userHaveProject(userId, projectId)) {
            return "redirect:/user/" + userId + "/project/all";
        }

        Project project = projectService.readById(projectId);

        model.addAttribute("project", project);
        model.addAttribute("userId", userId);
        return "project-info";
    }

    @GetMapping("/{projectId}/update")
    public String update(@PathVariable("projectId") Long projectId,
                         @PathVariable("userId") Long userId,
                         Model model) {
        if (!projectService.userHaveProject(userId, projectId)) {
            return "redirect:/user/" + userId + "/project/all";
        }

        Project project = projectService.readById(projectId);

        model.addAttribute("project", project);
        model.addAttribute("userId", userId);
        return "project-update";
    }

    @PostMapping("/{projectId}/update")
    public String update(@PathVariable("projectId") Long projectId,
                         @PathVariable("userId") Long userId,
                         @ModelAttribute("project") Project updatedProject) {

        if (projectService.userHaveProject(userId, projectId)) {
            Project project = projectService.readById(projectId);
            updatedProject.setOwners(project.getOwners());
            updatedProject.setId(projectId);
            projectService.create(updatedProject);
        }

        return "redirect:/user/" + userId + "/project/" + projectId;
    }

    @GetMapping("/{projectId}/delete")
    public String delete(@PathVariable("projectId") Long projectId,
                         @PathVariable("userId") Long userId) {

        if (projectService.userHaveProject(userId, projectId)) {
            projectService.delete(projectId);
        }

        return "redirect:/user/" + userId + "/project/all";
    }

}
