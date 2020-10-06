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

import com.qa.todo.dto.TaskListDTO;
import com.qa.todo.persistance.domain.TaskList;
import com.qa.todo.service.TaskListService;


@RestController
@CrossOrigin
@RequestMapping("/tasklist")
public class TaskListController {

	private TaskListService service;
	
	@Autowired
	public TaskListController(TaskListService service) {
		super();
		this.service = service;
	}

	//create
	@PostMapping("/create")
	public ResponseEntity<TaskListDTO> create(@RequestBody TaskList tasklist) {
		TaskListDTO created = this.service.create(tasklist);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	
	//readAll
	@GetMapping("/readAll")
	public ResponseEntity<List<TaskListDTO>> readAll() {
		return ResponseEntity.ok(this.service.readAll());	
	}
	
	
	//readOne
	@GetMapping("/readOne/{ID}")
	public ResponseEntity<TaskListDTO> readOne(@PathVariable Long ID) {
		return ResponseEntity.ok(this.service.readOne(ID));
	}
	
	
	//update
	@PutMapping("/update/{ID}")
	public ResponseEntity<TaskListDTO> update(@PathVariable Long ID, @RequestBody TaskListDTO tasklistDTO) {
		TaskListDTO updated = this.service.update(tasklistDTO, ID);
		return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	}
	
	//delete
	@DeleteMapping("/delete/{ID}")
	public ResponseEntity<TaskListDTO> delete(@PathVariable Long ID) {
		return this.service.delete(ID)
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
