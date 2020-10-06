package com.qa.todo.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.todo.dto.TaskDTO;
import com.qa.todo.persistance.domain.Task;
import com.qa.todo.service.TaskService;


@SpringBootTest
public class TaskControllerUnitTest {

	@Autowired
	private TaskController controller;
	
	@Autowired
	private ModelMapper mapper;
	
	@MockBean
    private TaskService service;

    private List<Task> tasks;
    private Task testTask;
    private Task testTaskWithID;
    private TaskDTO taskDTO;
    private final Long id = 1L;

    private TaskDTO mapToDTO(Task task) {
        return this.mapper.map(task, TaskDTO.class);
    }

    @BeforeEach
    void init() {
        this.tasks = new ArrayList<>();
        this.testTask = new Task("Ronaldo");
        this.testTaskWithID = new Task(testTask.getName());
        this.testTaskWithID.setId(id);
        this.tasks.add(testTaskWithID);
        this.taskDTO = this.mapToDTO(testTaskWithID);
    }

    @Test
    void createTest() {
        when(this.service.create(testTask))
            .thenReturn(this.taskDTO);
        
        assertThat(new ResponseEntity<PlayerDTO>(this.taskDTO, HttpStatus.CREATED))
                .isEqualTo(this.controller.create(testTask));
        
        verify(this.service, times(1))
            .createPlayer(this.testTask);
    }

    @Test
    void readOneTest() {
        when(this.service.readOne(this.id))
            .thenReturn(this.taskDTO);
        
        assertThat(new ResponseEntity<PlayerDTO>(this.taskDTO, HttpStatus.OK))
                .isEqualTo(this.controller.readOne(this.id));
        
        verify(this.service, times(1))
            .readOne(this.id);
    }

    @Test
    void readAllPlayersTest() {
        when(service.readAllPlayers())
            .thenReturn(this.players
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList()));
        
        assertThat(this.controller.readAllPlayers().getBody()
                .isEmpty()).isFalse();
        
        verify(this.service, times(1))
            .readAllPlayers();
    }

    @Test
    void updateTest() {
        // given
        PlayerDTO newPlayer= new PlayerDTO(null, "Messi", "RW");
        PlayerDTO updatedPlayer= new PlayerDTO(this.id, newPlayer.getName(), newPlayer.getPosition());

        when(this.service.update(newPlayer, this.id))
            .thenReturn(updatedPlayer);
        
        assertThat(new ResponseEntity<PlayerDTO>(updatedPlayer, HttpStatus.ACCEPTED))
                .isEqualTo(this.controller.update(this.id, newPlayer));
        
        verify(this.service, times(1))
            .update(newPlayer, this.id);
    }
    
    @Test
    void deleteTest() {
        this.controller.delete(id);

        verify(this.service, times(1))
            .delete(id);
    }
}
