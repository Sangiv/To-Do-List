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

import com.qa.todo.dto.TaskListDTO;
import com.qa.todo.persistance.domain.TaskList;
import com.qa.todo.service.TaskListService;

@SpringBootTest
public class TaskListControllerUnitTest {

	@Autowired
	private TaskListController controller;
	
	@Autowired
	private ModelMapper mapper;
	
	@MockBean
    private TaskListService service;

    private List<TaskList> tasklists;
    private TaskList testTasklist;
    private TaskList testTasklistWithID;
    private TaskListDTO tasklistDTO;
    private final Long id = 1L;

    private TaskListDTO mapToDTO(TaskList tasklist) {
        return this.mapper.map(tasklist, TaskListDTO.class);
    }

    @BeforeEach
    void init() {
        this.tasklists = new ArrayList<>();
        this.testTasklist = new TaskList("Arsenal");
        this.testTasklistWithID = new TaskList(testTasklist.getName());
        this.testTasklistWithID.setId(id);
        this.tasklists.add(testTasklistWithID);
        this.tasklistDTO = this.mapToDTO(testTasklistWithID);
    }

    @Test
    void createTest() {
        when(this.service.createClub(testTasklist))
            .thenReturn(this.tasklistDTO);
        
        assertThat(new ResponseEntity<ClubDTO>(this.tasklistDTO, HttpStatus.CREATED))
                .isEqualTo(this.controller.create(testTasklist));
        
        verify(this.service, times(1))
            .createClub(this.testTasklist);
    }

    @Test
    void readOneTest() {
        when(this.service.readOne(this.id))
            .thenReturn(this.tasklistDTO);
        
        assertThat(new ResponseEntity<ClubDTO>(this.tasklistDTO, HttpStatus.OK))
                .isEqualTo(this.controller.readOne(this.id));
        
        verify(this.service, times(1))
            .readOne(this.id);
    }

    @Test
    void readAllClubsTest() {
        when(service.readAllClubs())
            .thenReturn(this.tasklists
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList()));
        
        assertThat(this.controller.readAllClubs().getBody()
                .isEmpty()).isFalse();
        
        verify(this.service, times(1))
            .readAllClubs();
    }

    @Test
    void updateTest() {
        // given
        ClubDTO newClub= new ClubDTO(null, "BVB", 250000000L, null);
        ClubDTO updatedClub= new ClubDTO(this.id, newClub.getName(), newClub.getValue(), null);

        when(this.service.update(newClub, this.id))
            .thenReturn(updatedClub);
        
        assertThat(new ResponseEntity<ClubDTO>(updatedClub, HttpStatus.ACCEPTED))
                .isEqualTo(this.controller.update(this.id, newClub));
        
        verify(this.service, times(1))
            .update(newClub, this.id);
    }
    
    @Test
    void deleteTest() {
        this.controller.delete(id);

        verify(this.service, times(1))
            .delete(id);
    }
}
