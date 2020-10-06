package com.qa.todo.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.todo.persistance.domain.TaskList;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {

}
