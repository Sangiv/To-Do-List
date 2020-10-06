package com.qa.todo.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.todo.dto.TaskListDTO;
import com.qa.todo.persistance.domain.TaskList;
import com.qa.todo.persistance.repository.TaskListRepository;


@SpringBootTest
@AutoConfigureMockMvc
public class TaskListControllerIntegrationTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private TaskListRepository repo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private TaskList testTasklist;
    private TaskList testTasklistWithID;
    private TaskListDTO tasklistDTO;

    private Long id;
    private String testName;

    private TaskListDTO mapToDTO(TaskList tasklist) {
        return this.modelMapper.map(tasklist, TaskListDTO.class);
    }

    @BeforeEach
    void init() {
        this.repo.deleteAll();

        this.testTasklist = new TaskList("Arsenal");
        this.testTasklistWithID = this.repo.save(this.testTasklist);
        this.tasklistDTO = this.mapToDTO(testTasklistWithID);

        this.id = this.testTasklistWithID.getId();
        this.testName = this.testTasklistWithID.getName();
    }

    @Test
    void testCreate() throws Exception {
        this.mock
                .perform(request(HttpMethod.POST, "/tasklist/create").contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(testTasklist))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(this.objectMapper.writeValueAsString(tasklistDTO)));
    }

    @Test
    void testRead() throws Exception {
        this.mock.perform(request(HttpMethod.GET, "/tasklist/readOne/" + this.id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(this.objectMapper.writeValueAsString(this.tasklistDTO)));
    }

    @Test
    void testReadAll() throws Exception {
        List<TaskListDTO> tasklists = new ArrayList<>();
        tasklists.add(this.tasklistDTO);

        String content = this.mock
                .perform(request(HttpMethod.GET, "/tasklist/readAll").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(this.objectMapper.writeValueAsString(tasklists), content);
    }

    @Test
    void testUpdate() throws Exception {
        TaskListDTO newTasklist = new TaskListDTO(null, "BVB", null);
        TaskList updatedTasklist = new TaskList(newTasklist.getName());
        updatedTasklist.setId(this.id);

        String result = this.mock
                .perform(request(HttpMethod.PUT, "/tasklist/update/" + this.id).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(newTasklist)))
                .andExpect(status().isAccepted()).andReturn().getResponse().getContentAsString();

        assertEquals(this.objectMapper.writeValueAsString(this.mapToDTO(updatedTasklist)), result);
    }

    @Test
    void testDelete() throws Exception {
        this.mock.perform(request(HttpMethod.DELETE, "/tasklist/delete/" + this.id)).andExpect(status().isNoContent());
    }

}
