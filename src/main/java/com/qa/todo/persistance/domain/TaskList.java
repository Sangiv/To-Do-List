package com.qa.todo.persistance.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TaskList {

		@Id //Primary Key
		@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremented
		private Long id;
		
		@Column(name= "tasklist_name")
		@NotNull
		@Size(min= 1, max= 120)
		private String name;
//																	, fetch = FetchType.EAGER
		@OneToMany(mappedBy = "tasklist", cascade = CascadeType.ALL)
		private List<Task> tasks = new ArrayList<>();

		public TaskList(@NotNull @Size(min = 1, max = 120) String name) {
			super();
			this.name = name;
		}
		
}