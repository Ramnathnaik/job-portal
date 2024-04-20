package com.jobprotal.getintouch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class Candidate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Please provide a username")
	private String name;
	
	private String bio;
	
	@Email(message = "Please provide a valid email address")
	private String email;
	
	@NotNull(message = "Please provide a phone number")
	@Pattern(regexp="\\d{10}", message="Phone number must be 10 digits")
	private String phone;
	
	private String experience;
	
	private String education;
	
	private String location;
	
	private String currentCompany;
	
	private String currentPosition;
	
	private double currentSalary;
	
	private double preferredSalary;
	
	private String employmentType;
	
	private String noticePeriod;

}
