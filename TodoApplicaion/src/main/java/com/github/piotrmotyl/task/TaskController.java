package com.github.piotrmotyl.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    public static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository taskRepository;
    private final TaskCrudeRepository taskCrudeRepository;

    public TaskController(TaskRepository taskRepository,
                          TaskCrudeRepository taskCrudeRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskCrudeRepository = taskCrudeRepository;
    }

    @PostMapping
    ResponseEntity<Task> createTask(@RequestBody @Valid Task toCreate) {
        logger.info("===*** createTask method was called ***===");
        Task result = taskRepository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PostMapping("/form")
    public String createTask(@RequestParam("description") String description,
                             @RequestParam("taskDeadline") String taskDeadline,
                             Model model) {
        logger.info("===*** createTask + \"/form\"method was called ***===");

        Task newTask = new Task();
        newTask.setDescription(description);
        newTask.setTaskDeadline(LocalDateTime.parse(taskDeadline));

        taskRepository.save(newTask);

        model.addAttribute("tasks", taskRepository.findAll());
        model.addAttribute("task", new Task());
        return "tasks";
    }

    @GetMapping
    String showTasks(Model model) {
        logger.info("===*** showTasks method was called ***===");
        List<Task> tasks = taskRepository.findAll();

        model.addAttribute("tasks", tasks);
        model.addAttribute("task", new Task());
        return "tasks";
    }

    @GetMapping("/list")
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.info("===*** readAllTasks method was called ***===");
        return ResponseEntity.ok(taskRepository.findAll(page).getContent());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> readTask(@PathVariable int taskId) {
        logger.info("===*** readTask+(\"/{taskId}\") method was called ***===");
        return taskRepository.findById(taskId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping("/taskDone")
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue = "true") boolean state) {
        logger.info("===*** readDoneTasks method was called ***===");
        return ResponseEntity.ok(taskRepository.findByTaskDone(state));
    }

    @Transactional
    @PutMapping("/{taskId}")
    ResponseEntity<?> updateTask(@PathVariable int taskId, @RequestBody @Valid Task taskToUpdate) {
        logger.info("===*** updateTask+(\"/{taskId}\") method was called ***===");
        if (!taskRepository.existsById(taskId)) {
            return ResponseEntity.notFound().build();
        }

        taskRepository.findById(taskId)
                .ifPresent(task -> {
                    task.updateTaskFrom(taskToUpdate);
                    taskRepository.save(task);
                });
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PatchMapping("/{taskId}")
    public ResponseEntity<?> toggleTask(@PathVariable int taskId) {
        logger.info("===*** toggleTask+(\"/{taskId}\") method was called ***===");
        if (!taskRepository.existsById(taskId)) {
            return ResponseEntity.notFound().build();
        }

        taskRepository.findById(taskId)
                .ifPresent(task -> {
                    task.setTaskDone(!task.getTaskDone());                    ;
                    taskRepository.save(task);
                });

        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("/{taskId}")
    ResponseEntity<Task> deleteTask(@PathVariable @Valid int taskId) {
        logger.info("===*** deleteTask+(\"/{taskId}\") method was called ***===");
        if (!taskRepository.existsById(taskId)) {
            return ResponseEntity.notFound().build();
        }

        taskRepository.findById(taskId)
                .ifPresent(taskCrudeRepository::delete);
        return ResponseEntity.noContent().build();
    }
}