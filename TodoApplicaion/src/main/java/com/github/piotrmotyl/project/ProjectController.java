package com.github.piotrmotyl.project;

import com.github.piotrmotyl.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    public static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    private final ProjectService projectService;
    private final ProjectRepository projectRepository;
    private final ProjectCrudeRepository projectCrudeRepository;


    public ProjectController(ProjectService projectService, ProjectRepository projectRepository, ProjectCrudeRepository projectCrudeRepository) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
        this.projectCrudeRepository = projectCrudeRepository;
    }

    @GetMapping
    String showProjects(Model model) {
        logger.info("===*** showProjects method was called ***===");
        List<Project> projects = projectRepository.findAll();

        model.addAttribute("projects", projects);
        model.addAttribute("project", new Project());
        return "projects";
    }

    @GetMapping("/list")
    ResponseEntity<List<Project>> readAllProjects(Pageable page) {
        logger.info("===*** readAllProjects method was called ***===");
        return ResponseEntity.ok(projectRepository.findAll(page).getContent());
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> readProject(@PathVariable int projectId) {
        logger.info("===*** @readProject + (\"/{projectsId}\") method was called ***===");
        return projectRepository.findById(projectId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<Project> createProject(@RequestBody @Valid Project toCreate) {
        logger.info("===*** createProject method was called ***===");
        Project result = projectRepository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PostMapping("/form")
    String addProject(@ModelAttribute("project") Project project,
                      BindingResult bindingResult,
                      Model model)
    {
        logger.info("===*** addProject method was called ***===");
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "=== ERROR while loading project ===");
            model.addAttribute("projects", projectRepository.findAll());
            return "projects";
        }
        projectRepository.save(project);

        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("project", new Project());
        model.addAttribute("message", "===Project has been added===");

        return "projects";
    }

    @PostMapping("/{projectId}/tasks")
    public String addTaskToProject(@PathVariable int projectId,
                                   @RequestParam("description") String description,
                                   @RequestParam("taskDeadline") String taskDeadline,
                                   Model model)
    {
        logger.info("===*** addTaskToProject method was called ***===");

        if (projectRepository.findById(projectId).isEmpty()) {
            model.addAttribute("message", "=== cannot find Task with given id ===");
            model.addAttribute("projects", projectRepository.findAll());
            model.addAttribute("project", new Project());
            return "projects";

        } else if (projectRepository.findById(projectId).isEmpty() || description == null || description.isEmpty() || taskDeadline == null || taskDeadline.isEmpty()) {
            model.addAttribute("message", "=== Please add deadline date or fill description ===");
            model.addAttribute("projects", projectRepository.findAll());
            model.addAttribute("project", new Project());
            return "projects";
        }

        Task newTask = new Task();
        newTask.setDescription(description);
        newTask.setTaskDeadline(LocalDateTime.parse(taskDeadline));

        projectService.addTaskToProject(projectId, newTask);

        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("project", new Project());
        model.addAttribute("message", "=== Task has been added to the project ===");
        return "projects";
    }

    @Transactional
    @PutMapping("/{projectId}")
    ResponseEntity<?> updateProject(@PathVariable int projectId, @RequestBody @Valid Project projectToUpdate) {
        logger.info("===*** updateProject+(\"/{projectId}\") method was called ***===");
        if (!projectRepository.existsById(projectId)) {
            return ResponseEntity.notFound().build();
        }

        projectRepository.findById(projectId)
                .ifPresent(project -> {
                    project.updateProjectFrom(projectToUpdate);
                    projectRepository.save(project);
                });
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("/{projectId}")
    ResponseEntity<Project> deleteProject(@PathVariable @Valid int projectId) {
        logger.info("===*** deleteTask+(\"/{projectId}\") method was called ***===");
        if (!projectRepository.existsById(projectId)) {
            return ResponseEntity.notFound().build();
        }

        projectRepository.findById(projectId)
                .ifPresent(projectCrudeRepository::delete);
        return ResponseEntity.noContent().build();
    }

    @ModelAttribute("projects")
    List<Project> getProjects() {
        logger.info("===*** getProjects method was called ***===");
        return projectService.readAll();
    }
}