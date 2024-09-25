package com.github.piotrmotyl.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.piotrmotyl.project.Project;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Empty description - add something")
    private String description;

    @Column
    private boolean taskDone;

    @Column
    private LocalDateTime taskDeadline;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Task() {
    }
    @JsonIgnore
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getTaskDone() {
        return taskDone;
    }

    public void setTaskDone(boolean taskDone) {
        this.taskDone = taskDone;
    }

    public LocalDateTime getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(LocalDateTime taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public void updateTaskFrom(final Task taskSource) {
        description = taskSource.description;
        taskDone = taskSource.taskDone;
        taskDeadline = taskSource.taskDeadline;
    }
}