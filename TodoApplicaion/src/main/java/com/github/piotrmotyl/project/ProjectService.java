package com.github.piotrmotyl.project;

import com.github.piotrmotyl.task.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> readAll() {
        return projectRepository.findAll();
    }

    public void addTaskToProject(int projectId, Task task) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("=== No project with ID: " + projectId + " ==="));

        project.addTask(task);
        projectRepository.save(project);
    }
}