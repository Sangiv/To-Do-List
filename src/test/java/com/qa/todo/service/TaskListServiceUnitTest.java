package com.qa.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
class TaskListServiceUnitTest {

    @Autowired
    private TaskListService service;

    @MockBean
    private TaskListRepository repo;

    @MockBean
    private ModelMapper modelMapper;

    private List<TaskList> tasklists;
    private TaskList testTasklist;
    private TaskList testTasklistWithId;
    private TaskListDTO tasklistDTO;

    final Long id = 1L;
    final String testName = "Arsenal";

    @BeforeEach
    void init() {
        this.tasklists= new ArrayList<>();
        this.testTasklist = new TaskList(testName);
        this.tasklists.add(testTasklist);
        this.testTasklistWithId = new TaskList(testTasklist.getName());
        this.testTasklistWithId.setId(id);
        this.tasklistDTO = modelMapper.map(testTasklistWithId, TaskListDTO.class);
    }

    @Test
    void createTest() {

        when(this.repo.save(this.testTasklist)).thenReturn(this.testTasklistWithId);

        when(this.modelMapper.map(this.testTasklistWithId, TaskListDTO.class)).thenReturn(this.tasklistDTO);

        TaskListDTO expected = this.tasklistDTO;
        TaskListDTO actual = this.service.create(this.testTasklist);
        assertThat(expected).isEqualTo(actual);

        verify(this.repo, times(1)).save(this.testTasklist);
    }

    @Test
    void readOneTest() {

        when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testTasklistWithId));

        when(this.modelMapper.map(this.testTasklistWithId, TaskListDTO.class)).thenReturn(this.tasklistDTO);

        assertThat(this.tasklistDTO).isEqualTo(this.service.readOne(this.id));

        verify(this.repo, times(1)).findById(this.id);
    }

    @Test
    void readAllTest() {

        when(this.repo.findAll()).thenReturn(this.tasklists);

        when(this.modelMapper.map(this.testTasklistWithId, TaskListDTO.class)).thenReturn(this.tasklistDTO);

        assertThat(this.service.readAll().isEmpty()).isFalse();

        verify(this.repo, times(1)).findAll();
    }

    @Test
    void updateTest() {
        TaskList tasklist = new TaskList("BVB");
        tasklist.setId(this.id);

        TaskListDTO tasklistDTO = new TaskListDTO(null, "BVB", null);

        TaskList updatedTasklist = new TaskList(tasklistDTO.getName());
        updatedTasklist.setId(this.id);

        TaskListDTO updatedTasklistDTO = new TaskListDTO(this.id, updatedTasklist.getName(), null);

        when(this.repo.findById(this.id)).thenReturn(Optional.of(tasklist));

        when(this.repo.save(tasklist)).thenReturn(updatedTasklist);

        when(this.modelMapper.map(updatedTasklist, TaskListDTO.class)).thenReturn(updatedTasklistDTO);

        assertThat(updatedTasklistDTO).isEqualTo(this.service.update(tasklistDTO, this.id));

        verify(this.repo, times(1)).findById(1L);
        verify(this.repo, times(1)).save(updatedTasklist);
    }

    @Test
    void deleteTest() {

        when(this.repo.existsById(id)).thenReturn(true, false);

        assertThat(this.service.delete(id)).isTrue();

        verify(this.repo, times(1)).deleteById(id);

        verify(this.repo, times(2)).existsById(id);
    }

}