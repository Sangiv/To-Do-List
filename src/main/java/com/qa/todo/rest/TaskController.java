package com.qa.todo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.todo.dto.TaskDTO;
import com.qa.todo.persistance.domain.Task;
import com.qa.todo.service.TaskService;


@RestController
@CrossOrigin
@RequestMapping("/task")
public class TaskController {

	private TaskService service;
	
	@Autowired
	public TaskController(TaskService service) {
		super();
		this.service = service;
	}

	//create
	@PostMapping("/create")
	public ResponseEntity<TaskDTO> create(@RequestBody Task task) {
		TaskDTO created = this.service.create(task);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	
	//readAll
	@GetMapping("/readAll")
	public ResponseEntity<List<TaskDTO>> readAll() {
		return ResponseEntity.ok(this.service.readAll());	
	}
	
	
	//readOne
	@GetMapping("/readOne/{ID}")
	public ResponseEntity<TaskDTO> readOne(@PathVariable Long ID) {
		return ResponseEntity.ok(this.service.readOne(ID));
	}
	
	
	//update
	@PutMapping("/update/{ID}")
	public ResponseEntity<TaskDTO> update(@PathVariable Long ID, @RequestBody TaskDTO taskDTO) {
		TaskDTO updated = this.service.update(taskDTO, ID);
		return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	}
	
	//delete
	@DeleteMapping("/delete/{ID}")
	public ResponseEntity<TaskDTO> delete(@PathVariable Long ID) {
		return this.service.delete(ID)
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
