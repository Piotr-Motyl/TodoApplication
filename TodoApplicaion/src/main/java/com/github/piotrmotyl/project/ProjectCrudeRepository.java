package com.github.piotrmotyl.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ProjectCrudeRepository extends ProjectRepository, JpaRepository<Project, Integer> {

}
