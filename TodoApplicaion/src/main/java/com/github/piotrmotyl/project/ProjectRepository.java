package com.github.piotrmotyl.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    List<Project> findAll();
    Page<Project> findAll(Pageable page);
    Optional<Project> findById(Integer id);
    Project save(Project project);
    boolean existsById(Integer id);
}