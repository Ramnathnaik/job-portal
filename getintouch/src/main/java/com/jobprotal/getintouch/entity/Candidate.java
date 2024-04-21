package com.jobprotal.getintouch.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class Candidate implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Please provide your name")
	private String name;
	
	@NotEmpty(message = "Please provide a username")
	private String username;
	
	@NotBlank(message = "Password cannot be blank")
	private String password;
	
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
	
	@Enumerated(EnumType.STRING)
	private EmploymentType employmentType;
	
	private String noticePeriod;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCurrentCompany() {
		return currentCompany;
	}

	public void setCurrentCompany(String currentCompany) {
		this.currentCompany = currentCompany;
	}

	public String getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}

	public double getCurrentSalary() {
		return currentSalary;
	}

	public void setCurrentSalary(double currentSalary) {
		this.currentSalary = currentSalary;
	}

	public double getPreferredSalary() {
		return preferredSalary;
	}

	public void setPreferredSalary(double preferredSalary) {
		this.preferredSalary = preferredSalary;
	}

	public EmploymentType getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(EmploymentType employmentType) {
		this.employmentType = employmentType;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	@Override
	public String toString() {
		return "Candidate [id=" + id + ", name=" + name + ", username=" + username + ", bio=" + bio + ", email=" + email
				+ ", phone=" + phone + ", experience=" + experience + ", education=" + education + ", location="
				+ location + ", currentCompany=" + currentCompany + ", currentPosition=" + currentPosition
				+ ", currentSalary=" + currentSalary + ", preferredSalary=" + preferredSalary + ", employmentType="
				+ employmentType + ", noticePeriod=" + noticePeriod + "]";
	}
	
	private static enum EmploymentType {
		FULLTIME,
		PARTTIME
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
