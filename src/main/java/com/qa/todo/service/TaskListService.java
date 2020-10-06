package com.qa.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.qa.todo.dto.TaskListDTO;
import com.qa.todo.exception.TaskListNotFoundException;
import com.qa.todo.persistance.domain.TaskList;
import com.qa.todo.persistance.repository.TaskListRepository;
import com.qa.todo.utils.todoBeanUtils;

@Service
public class TaskListService {
	
	private TaskListRepository repo;
	
	private ModelMapper mapper;
	
	@Autowired
	public TaskListService(TaskListRepository repo, ModelMapper model) {
		super();
		this.repo = repo;
		this.mapper = model;
	}
	
	private TaskListDTO mapToDTO(TaskList tasklist) {
		return this.mapper.map(tasklist, TaskListDTO.class);
	}
	
	
	//create
	public TaskListDTO create(TaskList tasklist) {
		TaskList saved = this.repo.save(tasklist);
		return this.mapToDTO(saved);
	}
	
	//readAll
	public List<TaskListDTO> readAll() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	//readByID
	public TaskListDTO readOne(Long ID) {
		TaskList found = this.repo.findById(ID).orElseThrow(TaskListNotFoundException::new);
		return this.mapToDTO(found);
	}
	
	//update
	public TaskListDTO update(TaskListDTO tasklistDTO, Long ID) {
		TaskList toUpdate = this.repo.findById(ID).orElseThrow(TaskListNotFoundException::new);
		todoBeanUtils.mergeObject(tasklistDTO, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
	}
	
	//delete
	public boolean delete(Long ID) {
        if (!this.repo.existsById(ID)) {
            throw new TaskListNotFoundException();
        }
        this.repo.deleteById(ID);
        return !this.repo.existsById(ID);
    }
	
	
}
