package ua.casten.user.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.casten.user.project.model.Project;
import ua.casten.user.project.model.Task;
import ua.casten.user.project.service.ProjectService;
import ua.casten.user.project.service.TaskService;

import java.util.List;

@Controller
@RequestMapping("/user/{userId}/project/{projectId}/task")
public class TaskController {

    private final ProjectService projectService;
    private final TaskService taskService;

    @Autowired
    public TaskController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public String read(@PathVariable("projectId") Long projectId,
                       @PathVariable("userId") Long userId,
                       Model model) {
        if (!projectService.userHaveProject(userId, projectId)) {
            return "redirect:/users/" + userId + "/read";
        }
        Project project = projectService.readById(projectId);

        List<Task> tasks = taskService.getByProjectId(projectId);
        model.addAttribute("tasks", tasks);
        model.addAttribute("project", project);
        model.addAttribute("userId", userId);

        return "project-tasks";
    }

}
