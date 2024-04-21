package com.jobprotal.getintouch.controller;

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

import com.jobprotal.getintouch.entity.Candidate;
import com.jobprotal.getintouch.response.JobPortalResponse;
import com.jobprotal.getintouch.service.CandidateService;

import jakarta.validation.Valid;

import static com.jobprotal.getintouch.response.LoggerStatus.STARTED;
import static com.jobprotal.getintouch.response.LoggerStatus.COMPLETED;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CandidateController.class);

	@Autowired
	private CandidateService candidateService;

	@GetMapping("/{candidateId}")
	public ResponseEntity<JobPortalResponse<?>> getCandidate(@PathVariable Long candidateId) {
		LOGGER.info("getCandidate: {}", STARTED);
		Candidate candidate = this.candidateService.getCandidate(candidateId);
		JobPortalResponse<Candidate> jobPortalResponse = JobPortalResponse.success(candidate,
				"data for candidate with ID: " + candidate.getId());
		LOGGER.info("getCandidate: {}", COMPLETED);
		return ResponseEntity.ok(jobPortalResponse);
	}

	@PostMapping
	public ResponseEntity<JobPortalResponse<?>> createCandidate(@Valid @RequestBody Candidate candidate,
			BindingResult bindingResult) {
		LOGGER.info("createCandidate: {}", STARTED);
		if (bindingResult.hasErrors()) {
			Map<String, String> validationErrors = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				validationErrors.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(JobPortalResponse.failure(validationErrors, "validation errors"));
		}
		
		Candidate newCandidate = this.candidateService.createCandidate(candidate);
		JobPortalResponse<Candidate> jobPortalResponse = JobPortalResponse.success(newCandidate,
				"candidate created successfully");
		LOGGER.info("createCandidate: {}", COMPLETED);
		return new ResponseEntity(jobPortalResponse, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<JobPortalResponse<?>> getAllCandidates() {
		LOGGER.info("getAllCandidates: {}", STARTED);
		List<Candidate> allCandidates = this.candidateService.getAllCandidates();
		JobPortalResponse<List<Candidate>> jobPortalResponse = JobPortalResponse.success(allCandidates,
				"all candidates info");
		LOGGER.info("getAllCandidates: {}", COMPLETED);
		return ResponseEntity.ok(jobPortalResponse);
	}

	@PutMapping
	public ResponseEntity<JobPortalResponse<?>> updateCandidate(@RequestBody Candidate candidate) {
		LOGGER.info("updateCandidate: {}", STARTED);
		Candidate updateCandidate = this.candidateService.updateCandidate(candidate);
		JobPortalResponse<Candidate> jobPortalResponse = JobPortalResponse.success(updateCandidate,
				"candidate updated successfully");
		LOGGER.info("updateCandidate: {}", COMPLETED);
		return ResponseEntity.ok(jobPortalResponse);
	}

	@DeleteMapping("/{candidateId}")
	public ResponseEntity<JobPortalResponse<?>> deleteCandidate(@PathVariable Long candidateId) {
		LOGGER.info("deleteCandidate: {}", STARTED);
		this.candidateService.deleteCandidate(candidateId);
		JobPortalResponse jobPortalResponse = JobPortalResponse
				.success("candidate deleted successfully");
		LOGGER.info("deleteCandidate: {}", COMPLETED);
		return ResponseEntity.ok(jobPortalResponse);
	}

}
