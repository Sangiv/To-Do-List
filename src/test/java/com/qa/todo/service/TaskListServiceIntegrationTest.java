package com.qa.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.todo.dto.TaskListDTO;
import com.qa.todo.persistance.domain.TaskList;
import com.qa.todo.persistance.repository.TaskListRepository;


@SpringBootTest
class TaskListServiceIntegrationTest {

    @Autowired
    private TaskListService service;

    @Autowired
    private TaskListRepository repo;

    @MockBean
    private ModelMapper modelMapper;

    private TaskList testTasklist;
    private TaskList testTasklistWithId;
    private TaskListDTO testTasklistDTO;

    private Long id;
    private final String name = "Arsenal";

    @BeforeEach
    void init() {
        this.repo.deleteAll();
        this.testTasklist = new TaskList(name);
        this.testTasklistWithId = this.repo.save(this.testTasklist);
        this.testTasklistDTO = modelMapper.map(testTasklistWithId, TaskListDTO.class);
        this.id = this.testTasklistWithId.getId();
    }

    @Test
    void testCreate() {
    	
    	when(this.modelMapper.map(this.testTasklistWithId, TaskListDTO.class)).thenReturn(this.testTasklistDTO);
    	
        assertThat(this.testTasklistDTO)
            .isEqualTo(this.service.create(testTasklist));
    }

    @Test
    void testReadOne() {
    	
    	when(this.modelMapper.map(this.testTasklistWithId, TaskListDTO.class)).thenReturn(this.testTasklistDTO);
    	
        assertThat(this.testTasklistDTO)
                .isEqualTo(this.service.readOne(this.id));
    }

    @Test
    void testReadAll() {
    	
    	when(this.modelMapper.map(this.testTasklistWithId, TaskListDTO.class)).thenReturn(this.testTasklistDTO);
    	
        assertThat(this.service.readAll())
                .isEqualTo(Stream.of(this.testTasklistDTO)
                        .collect(Collectors.toList()));
    }

    @Test
    void testUpdate() {
    	
        TaskListDTO newTasklist = new TaskListDTO(null, "BVB", new ArrayList<>());
        TaskListDTO updatedTasklist = new TaskListDTO(this.id, newTasklist.getName(), new ArrayList<>());
        
    	when(this.modelMapper.map(this.testTasklistWithId, TaskListDTO.class)).thenReturn(this.testTasklistDTO);

        assertThat(updatedTasklist)
            .isEqualTo(this.service.update(newTasklist, this.id));
    }

    @Test
    void testDelete() {
        assertThat(this.service.delete(this.id)).isTrue();
    }

}
