package com.qa.todo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.todo.dto.TaskDTO;
import com.qa.todo.persistance.domain.Task;
import com.qa.todo.persistance.repository.TaskRepository;


@SpringBootTest
class TaskServiceIntegrationTest {

    @Autowired
    private TaskService service;

    @Autowired
    private TaskRepository repo;

    @Autowired
    private ModelMapper modelMapper;

    private TaskDTO mapToDTO(Task task) {
        return this.modelMapper.map(task, TaskDTO.class);
    }

    private Task testTask;
    private Task testTaskWithId;
    private TaskDTO testTaskDTO;

    private Long id;
    private final String name = "Arsenal";

    
    @BeforeEach
    void init() {
        this.repo.deleteAll();
        this.testTask= new Task(name);
        this.testTaskWithId = this.repo.save(this.testTask);
        this.testTaskDTO = this.mapToDTO(testTaskWithId);
        this.id = this.testTaskWithId.getId();
    }

    @Test
    void testCreate() {
        assertThat(this.testTaskDTO)
            .isEqualTo(this.service.createPlayer(testTask));
    }

    @Test
    void testReadOne() {
        assertThat(this.testTaskDTO)
                .isEqualTo(this.service.readOne(this.id));
    }

    @Test
    void testReadAll() {
        // check this one out with a breakpoint and running it in debug mode
        // so you can see the stream happening
        assertThat(this.service.readAllPlayers())
                .isEqualTo(Stream.of(this.testTaskDTO)
                        .collect(Collectors.toList()));
    }

    @Test
    void testUpdate() {
        PlayerDTO newPlayer = new PlayerDTO(null, "Messi", "RW");
        PlayerDTO updatedPlayer = new PlayerDTO(this.id, newPlayer.getName(), newPlayer.getPosition());

        assertThat(updatedPlayer)
            .isEqualTo(this.service.update(newPlayer, this.id));
    }

    @Test
    void testDelete() {
        assertThat(this.service.delete(this.id)).isTrue();
    }

}
