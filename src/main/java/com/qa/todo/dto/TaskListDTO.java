package com.qa.todo.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

//import com.qa.sangivspring.persistance.domain.Player;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TaskListDTO {
	
	private Long id;
	private String name;
	private List<TaskDTO> tasks;
	
}
