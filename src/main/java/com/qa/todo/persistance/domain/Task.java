package com.qa.todo.persistance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;




@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Task {

		@Id //Primary Key
		@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremented
		private Long id;
		
		@Column(name= "tsak_name")
		@NotNull
		@Size(min= 1, max= 120)
		private String name;
		
		@ManyToOne()
		private TaskList tasklist;

		public Task(@NotNull @Size(min = 1, max = 120) String name, TaskList tasklist) {
			super();
			this.name = name;
			this.tasklist = tasklist;
		}
		
		public Task(@NotNull @Size(min = 1, max = 120) String name) {
			super();
			this.name = name;
		}		
}
