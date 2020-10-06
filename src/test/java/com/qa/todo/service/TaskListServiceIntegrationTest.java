package com.qa.todo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.todo.dto.TaskDTO;
import com.qa.todo.dto.TaskListDTO;
import com.qa.todo.persistance.domain.Task;
import com.qa.todo.persistance.domain.TaskList;
import com.qa.todo.persistance.repository.TaskListRepository;


@SpringBootTest
class TaskListServiceIntegrationTest {

    @Autowired
    private TaskListService service;

    @Autowired
    private TaskListRepository repo;

    @Autowired
    private ModelMapper modelMapper;

    private TaskListDTO mapToDTO(TaskList tasklist) {
        return this.modelMapper.map(tasklist, TaskListDTO.class);
    }

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
        this.testTasklistDTO = this.mapToDTO(testTasklistWithId);
        this.id = this.testTasklistWithId.getId();
    }

    @Test
    void testCreate() {
        assertThat(this.testTasklistDTO)
            .isEqualTo(this.service.createClub(testTasklist));
    }

    @Test
    void testReadOne() {
        assertThat(this.testTasklistDTO)
                .isEqualTo(this.service.readOne(this.id));
    }

    @Test
    void testReadAll() {
        // check this one out with a breakpoint and running it in debug mode
        // so you can see the stream happening
        assertThat(this.service.readAllClubs())
                .isEqualTo(Stream.of(this.testTasklistDTO)
                        .collect(Collectors.toList()));
    }

    @Test
    void testUpdate() {
        ClubDTO newClub = new ClubDTO(null, "BVB", 250000000L, new ArrayList<>());
        ClubDTO updatedClub = new ClubDTO(this.id, newClub.getName(), newClub.getValue(), new ArrayList<>());

        assertThat(updatedClub)
            .isEqualTo(this.service.update(newClub, this.id));
    }

    @Test
    void testDelete() {
        assertThat(this.service.delete(this.id)).isTrue();
    }

}
