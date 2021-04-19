package ua.casten.user.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.casten.user.project.model.Priority;
import ua.casten.user.project.model.Project;
import ua.casten.user.project.model.Task;
import ua.casten.user.project.service.PriorityService;
import ua.casten.user.project.service.ProjectService;
import ua.casten.user.project.service.TaskService;
import ua.casten.user.project.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/user/{userId}/project/{projectId}/task")
public class TaskController {

    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final PriorityService priorityService;

    @Autowired
    public TaskController(UserService userService,
                          ProjectService projectService,
                          TaskService taskService,
                          PriorityService priorityService) {
        this.userService =  userService;
        this.projectService = projectService;
        this.taskService = taskService;
        this.priorityService = priorityService;
    }

    @GetMapping("/all")
    public String read(@PathVariable("projectId") Long projectId,
                       @PathVariable("userId") Long userId,
                       Model model) {
        if (!userService.userHasProject(userId, projectId)) {
            return "redirect:/user/" + userId + "/project/all";
        }
        Project project = projectService.readById(projectId);

        List<Task> tasks = taskService.getByProjectIdAndCompleted(projectId, false);
        model.addAttribute("tasks", tasks);
        model.addAttribute("project", project);

        return "task-list";
    }

    @GetMapping("/completed")
    public String completed(@PathVariable("projectId") Long projectId,
                       @PathVariable("userId") Long userId,
                       Model model) {
        if (!userService.userHasProject(userId, projectId)) {
            return "redirect:/user/" + userId + "/project/all";
        }
        Project project = projectService.readById(projectId);

        List<Task> tasks = taskService.getByProjectIdAndCompleted(projectId, true);
        model.addAttribute("tasks", tasks);
        model.addAttribute("project", project);

        return "task-completed";
    }

    @GetMapping("/create")
    public String create(@PathVariable("projectId") Long projectId,
                         @PathVariable("userId") Long userId,
                         Model model) {
        if (!userService.userHasProject(userId, projectId)) {
            return "redirect:/user/" + userId + "/project/all";
        }

        List<Priority> priorities = priorityService.getAll();
        model.addAttribute("priorities", priorities);
        model.addAttribute("task", new Task());
        return "task-create";
    }

    @PostMapping("/create")
    public String create(@PathVariable("projectId") Long projectId,
                         @PathVariable("userId") Long userId,
                         @ModelAttribute("task") Task task,
                         @RequestParam("priorityId") Long priorityId) {

        if (!userService.userHasProject(userId, projectId)) {
            return "redirect:/user/" + userId + "/project/all";
        }

        Priority priority = priorityService.readById(priorityId);
        Project project = projectService.readById(projectId);
        task.setPriority(priority);
        task.setProject(project);
        taskService.save(task);
        List<Task> tasks = project.getTasks();
        tasks.add(task);
        project.setTasks(tasks);
        projectService.save(project);

        return "redirect:/user/" + userId + "/project/" + projectId + "/task/all";
    }

    @GetMapping("/{taskId}")
    public String info(@PathVariable("userId") Long userId,
                       @PathVariable("projectId") Long projectId,
                       @PathVariable("taskId") Long taskId,
                       Model model) {

        if (!userService.userHasProject(userId, projectId)) {
            return "redirect:/user/" + userId + "/project/all";
        } else if (!projectService.projectHasTask(projectId, taskId)) {
            return "redirect:/user/" + userId + "/project/" + projectId + "/task/all";
        }

        Task task = taskService.readById(taskId);
        model.addAttribute("task", task);

        return "task-info";
    }

    @GetMapping("/{taskId}/update")
    public String update(@PathVariable("userId") Long userId,
                         @PathVariable("projectId") Long projectId,
                         @PathVariable("taskId") Long taskId,
                         Model model) {

        if (!userService.userHasProject(userId, projectId)) {
            return "redirect:/user/" + userId + "/project/all";
        } else if (!projectService.projectHasTask(projectId, taskId)) {
            return "redirect:/user/" + userId + "/project/" + projectId + "/task/all";
        }

        List<Priority> priorities = priorityService.getAll();
        Task task = taskService.readById(taskId);

        model.addAttribute("task", task);
        model.addAttribute("priorities", priorities);

        return "task-update";
    }

    @PostMapping("/{taskId}/update")
    public String update(@PathVariable("userId") Long userId,
                         @PathVariable("projectId") Long projectId,
                         @PathVariable("taskId") Long taskId,
                         @RequestParam("priorityId") Long priorityId,
                         @ModelAttribute("task") Task updatedTask) {

        if (!userService.userHasProject(userId, projectId)) {
            return "redirect:/user/" + userId + "/project/all";
        }

        if (projectService.projectHasTask(projectId, taskId)) {
            Project project = projectService.readById(projectId);
            Priority priority = priorityService.readById(priorityId);
            List<Task> projectTasks = project.getTasks();
            projectTasks.add(updatedTask);
            project.setTasks(projectTasks);
            updatedTask.setProject(project);
            updatedTask.setPriority(priority);
            updatedTask.setId(taskId);
            taskService.save(updatedTask);
            projectService.save(project);
        }

        return "redirect:/user/" + userId + "/project/" + projectId + "/task/all";
    }

    @GetMapping("/{taskId}/delete")
    public String delete(@PathVariable("userId") Long userId,
                         @PathVariable("projectId") Long projectId,
                         @PathVariable("taskId") Long taskId) {

        if (!userService.userHasProject(userId, projectId)) {
            return "redirect:/user/" + userId + "/project/all";
        }

        if (projectService.projectHasTask(projectId, taskId)) {
            taskService.delete(taskId);
        }

        return "redirect:/user/" + userId + "/project/" + projectId + "/task/all";
    }

}
