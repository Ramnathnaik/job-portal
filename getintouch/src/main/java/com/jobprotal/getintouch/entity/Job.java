package com.jobprotal.getintouch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Job {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "please provide a job title")
	private String title;
	
	private String description;
	
	@NotEmpty(message = "please provide the salary")
	private double salary;
	
	@NotEmpty(message = "please provide techstack")
	private String techstack;
	
	@NotEmpty(message = "please provide location")
	private String location;
	
	private String experience;
	
	private String domain;
	
	private Long companyId;

}
