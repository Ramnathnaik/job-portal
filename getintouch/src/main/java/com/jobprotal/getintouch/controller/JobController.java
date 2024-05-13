package com.jobprotal.getintouch.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.jobprotal.getintouch.entity.Job;
import com.jobprotal.getintouch.response.JobPortalResponse;
import com.jobprotal.getintouch.service.JobService;

import static com.jobprotal.getintouch.response.LoggerStatus.COMPLETED;
import static com.jobprotal.getintouch.response.LoggerStatus.STARTED;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/job")
public class JobController {

	private Logger LOGGER = LoggerFactory.getLogger(JobController.class);
	
	@Autowired
	private JobService jobService;
	
	@GetMapping("/{jobId}")
	public ResponseEntity<JobPortalResponse<?>> getJobDetails(@PathVariable String jobId) {
		LOGGER.info("getJobDetails: {}", STARTED);
		Job job = this.jobService.getJobDetails(jobId);
		JobPortalResponse jobPortalResponse = JobPortalResponse.success(job, "retreived job details");
		LOGGER.info("getJobDetails: {}", COMPLETED);
		return ResponseEntity.ok(jobPortalResponse);
	}
	
	@GetMapping
	public ResponseEntity<JobPortalResponse<?>> getJobs() {
		LOGGER.info("getJobs: {}", STARTED);
		List<Job> jobs = this.jobService.getJobs();
		JobPortalResponse jobPortalResponse = JobPortalResponse.success(jobs, "jobs retrived");
		LOGGER.info("getJobs: {}", COMPLETED);
		return ResponseEntity.ok(jobPortalResponse);
	}
	
	@PostMapping
	public ResponseEntity<JobPortalResponse<?>> createJob(@Valid @RequestBody Job job, BindingResult bindingResult) {
		LOGGER.info("createJob: {}", STARTED);
		if (bindingResult.hasErrors()) {
			Map<String, String> validationErrors = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				validationErrors.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(JobPortalResponse.failure(validationErrors, "validation errors"));
		}
		
		Job createdJob = this.jobService.createJob(job);
		JobPortalResponse jobPortalResponse = JobPortalResponse.success(createdJob, "created job successfully");
		LOGGER.info("createJob: {}", COMPLETED);
		return new ResponseEntity(jobPortalResponse, HttpStatus.CREATED);
		
	}
	
	@PutMapping
	public ResponseEntity<JobPortalResponse<?>> updateJob(@Valid @RequestBody Job job, BindingResult bindingResult) {
		LOGGER.info("updateJob: {}", STARTED);
		if (bindingResult.hasErrors()) {
			Map<String, String> validationErrors = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				validationErrors.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(JobPortalResponse.failure(validationErrors, "validation errors"));
		}
		
		Job updatedJob = this.jobService.updateJob(job);
		JobPortalResponse jobPortalResponse = JobPortalResponse.success(updatedJob, "job updated successfully");
		LOGGER.info("updateJob: {}", COMPLETED);
		return ResponseEntity.ok(jobPortalResponse);
	}
	
	@DeleteMapping("/{jobId}")
	public ResponseEntity<JobPortalResponse<?>> deleteJob(@PathVariable String jobId) {
		LOGGER.info("deleteJob: {}", STARTED);
		this.jobService.deleteJob(jobId);
		JobPortalResponse jobPortalResponse = JobPortalResponse.success("job deleted successfully");
		LOGGER.info("deleteJob: {}", COMPLETED);
		return ResponseEntity.ok(jobPortalResponse);
	}
}
