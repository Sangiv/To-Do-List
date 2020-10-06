package com.qa.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.qa.todo.dto.TaskDTO;
import com.qa.todo.exception.TaskNotFoundException;
import com.qa.todo.persistance.domain.Task;
import com.qa.todo.persistance.repository.TaskRepository;
import com.qa.todo.utils.todoBeanUtils;

@Service
public class TaskService {
	
	private TaskRepository repo;
	
	private ModelMapper mapper;
	
	@Autowired
	public TaskService(TaskRepository repo, ModelMapper model) {
		super();
		this.repo = repo;
		this.mapper = model;
	}
	
	private TaskDTO mapToDTO(Task task) {
		return this.mapper.map(task, TaskDTO.class);
	}
	
	//create
	public TaskDTO create(Task task) {
		Task saved = this.repo.save(task);
		return this.mapToDTO(saved);
	}
	
	//readAll
	public List<TaskDTO> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	//readByID
	public TaskDTO readOne(Long id) {
		Task found = this.repo.findById(id).orElseThrow(TaskNotFoundException::new);
		return this.mapToDTO(found);
	}
	
	//update
	public TaskDTO update(TaskDTO taskDTO, Long ID) {
		Task toUpdate = this.repo.findById(ID).orElseThrow(TaskNotFoundException::new);
		todoBeanUtils.mergeObject(taskDTO, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
	}
	
	//delete
	public boolean delete(Long ID) {
        if (!this.repo.existsById(ID)) {
            throw new TaskNotFoundException();
        }
        this.repo.deleteById(ID);
        return !this.repo.existsById(ID);
    }
}
