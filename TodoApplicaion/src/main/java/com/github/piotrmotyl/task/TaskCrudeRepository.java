package com.github.piotrmotyl.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface TaskCrudeRepository extends TaskRepository, JpaRepository<Task, Integer> {

    @Override
    @Query(nativeQuery = true, value = "select COUNT(*) > 0 from tasks where id=:id")
    boolean existsById(@Param("id") Integer id);
}