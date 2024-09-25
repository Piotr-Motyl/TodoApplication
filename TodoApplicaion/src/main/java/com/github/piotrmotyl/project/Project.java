package com.github.piotrmotyl.project;

import com.github.piotrmotyl.task.Task;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "=== Project's description cannot be empty ===")
    private String description;

    @Column
    private boolean projectDone;

    @Column()
    private LocalDateTime projectDeadline;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Project(){
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

    public boolean isProjectDone() {
        return projectDone;
    }

    public void setProjectDone(boolean projectDone) {
        this.projectDone = projectDone;
    }

    public LocalDateTime getProjectDeadline() {
        return projectDeadline;
    }

    public void setProjectDeadline(LocalDateTime projectDeadline) {
        this.projectDeadline = projectDeadline;
    }

    public void updateProjectFrom(final Project projectSource) {
        description = projectSource.description;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
        task.setProject(this);
    }
}