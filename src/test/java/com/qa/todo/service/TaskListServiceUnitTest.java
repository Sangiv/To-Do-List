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
        // a when() to set up our mocked repo
        when(this.repo.save(this.testClub)).thenReturn(this.testClubWithId);

        // and a when() to set up our mocked modelMapper
        when(this.modelMapper.map(this.testClubWithId, ClubDTO.class)).thenReturn(this.clubDTO);

        // check that the clubDTO set up as our <expected> value
        // is the same as the <actual> result when running the service.create()
        // method

        ClubDTO expected = this.clubDTO;
        ClubDTO actual = this.service.createClub(this.testClub);
        assertThat(expected).isEqualTo(actual);

        // we check that our mocked repository was hit - if it was, that means our
        // service works
        verify(this.repo, times(1)).save(this.testClub);
    }

    @Test
    void readOneTest() {
        // the repo spun up extends a Spring type of repo that uses Optionals
        // thus, when running a method in the repo (e.g. findById() ) we have to
        // return the object we want as an Optional (using Optional.of(our object) )
        when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testClubWithId));

        when(this.modelMapper.map(this.testClubWithId, ClubDTO.class)).thenReturn(this.clubDTO);

        assertThat(this.clubDTO).isEqualTo(this.service.readOne(this.id));

        verify(this.repo, times(1)).findById(this.id);
    }

    @Test
    void readAllTest() {
        // findAll() returns a list, which is handy, since we already have clubs spun up.
        when(this.repo.findAll()).thenReturn(this.tasklists);

        when(this.modelMapper.map(this.testClubWithId, ClubDTO.class)).thenReturn(this.clubDTO);

        assertThat(this.service.readAllClubs().isEmpty()).isFalse();

        verify(this.repo, times(1)).findAll();
    }

    @Test
    void updateTest() {
        Club club = new Club("BVB", 250000000L);
        club.setId(this.id);

        ClubDTO clubDTO = new ClubDTO(null, "BVB", 250000000L, null);

        Club updatedClub= new Club(clubDTO.getName(), clubDTO.getValue());
        updatedClub.setId(this.id);

        ClubDTO updatedClubDTO = new ClubDTO(this.id, updatedClub.getName(),
                updatedClub.getValue(), null);

        // finById() grabs a specific club out of the repo
        when(this.repo.findById(this.id)).thenReturn(Optional.of(club));

        // we then save() an updated club back to the repo
        // we'd normally save() it once we've done stuff to it, but instead we're just
        // feeding in an <expected>
        // that we set up at the top of this method ^
        when(this.repo.save(club)).thenReturn(updatedClub);

        when(this.modelMapper.map(updatedClub, ClubDTO.class)).thenReturn(updatedClubDTO);

        assertThat(updatedClubDTO).isEqualTo(this.service.update(clubDTO, this.id));

        // since we've ran two when().thenReturn() methods, we need to run a verify() on
        // each:
        verify(this.repo, times(1)).findById(1L);
        verify(this.repo, times(1)).save(updatedClub);
    }

    @Test
    void deleteTest() {
        // running this.repo.existsById(id) twice, hence two returns (true &
        // false)
        // the <true> and <false> get plugged in once each to our two verify() methods
        when(this.repo.existsById(id)).thenReturn(true, false);

        assertThat(this.service.delete(id)).isTrue();


        verify(this.repo, times(1)).deleteById(id);
        
        // this plugs in the <true> from our when().thenReturn()
        // this plugs in the <false> from our when().thenReturn()
        verify(this.repo, times(2)).existsById(id);
    }

}