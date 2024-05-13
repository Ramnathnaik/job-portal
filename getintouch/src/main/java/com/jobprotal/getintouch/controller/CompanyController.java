package com.jobprotal.getintouch.controller;

import static com.jobprotal.getintouch.response.LoggerStatus.COMPLETED;
import static com.jobprotal.getintouch.response.LoggerStatus.STARTED;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobprotal.getintouch.entity.Company;
import com.jobprotal.getintouch.response.JobPortalResponse;
import com.jobprotal.getintouch.service.CompanyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
public class CompanyController {
	private Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);
	
	@Autowired
	private CompanyService companyService;
	
	@PostMapping
	public ResponseEntity<JobPortalResponse<?>> registerCompany(@Valid @RequestBody Company company, BindingResult bindingResult) {
		LOGGER.info("registerCompany: {}", STARTED);
		if (bindingResult.hasErrors()) {
			Map<String, String> validationErrors = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				validationErrors.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(JobPortalResponse.failure(validationErrors, "validation errors"));
		}
		
		Company registeredCompany = this.companyService.registerCompany(company);
		JobPortalResponse<Company> jobPortalResponse = JobPortalResponse.success(registeredCompany, "Company registered successfully");
		LOGGER.info("registerCompany: {}", COMPLETED);
		return new ResponseEntity(jobPortalResponse, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<JobPortalResponse<?>> getCompanies() {
		LOGGER.info("getCompanies: {}", STARTED);
		List<Company> companies = this.companyService.getCompanies();
		JobPortalResponse<Company> jobPortalResponse = JobPortalResponse.success(companies, "All companies retreived");
		LOGGER.info("getCompanies: {}", COMPLETED);
		return ResponseEntity.ok(jobPortalResponse);
	}
	
	@GetMapping("/{companyId}")
	public ResponseEntity<JobPortalResponse<?>> getCompanyDetails(@PathVariable String companyId) {
		LOGGER.info("getCompanyDetails: {}", STARTED);
		Company company = this.companyService.getCompanyDetails(companyId);
		JobPortalResponse<Company> jobPortalResponse = JobPortalResponse.success(company, "Company details retreived");
		LOGGER.info("getCompanyDetails: {}", COMPLETED);
		return ResponseEntity.ok(jobPortalResponse);
	}
	
	@PutMapping
	public ResponseEntity<JobPortalResponse<?>> updateRegisteredCompany(@Valid @RequestBody Company company, BindingResult bindingResult) {
		LOGGER.info("registerCompany: {}", STARTED);
		if (bindingResult.hasErrors()) {
			Map<String, String> validationErrors = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				validationErrors.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(JobPortalResponse.failure(validationErrors, "validation errors"));
		}
		
		Company updatedCompany = this.companyService.updateRegisteredCompany(company);
		JobPortalResponse<Company> jobPortalResponse = JobPortalResponse.success(updatedCompany, "Company details updated successfully");
		LOGGER.info("registerCompany: {}", COMPLETED);
		return ResponseEntity.ok(jobPortalResponse);
	}
	
	@DeleteMapping("/{companyId}")
	public ResponseEntity<JobPortalResponse<?>> deleteRegisteredCompany(@PathVariable String companyId) {
		LOGGER.info("getCompanyDetails: {}", STARTED);
		this.companyService.deleteRegisteredCompany(companyId);
		JobPortalResponse<Company> jobPortalResponse = JobPortalResponse.success("Company details deleted successfully");
		LOGGER.info("getCompanyDetails: {}", COMPLETED);
		return ResponseEntity.ok(jobPortalResponse);
	}

}
