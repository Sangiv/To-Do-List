package com.qa.todo.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.todo.persistance.domain.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
