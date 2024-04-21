package com.jobprotal.getintouch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jobprotal.getintouch.response.JobPortalResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({CandidateNotFoundException.class})
	public ResponseEntity<JobPortalResponse<String>> handleCandidateNotFoundException(CandidateNotFoundException ex) {
		JobPortalResponse<String> jobPortalResponse = JobPortalResponse.failure(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jobPortalResponse);
	}
	
	@ExceptionHandler({CandidateAlreadyPresentException.class})
	public ResponseEntity<JobPortalResponse<String>> handleCandidateAlreadyPresentException(CandidateAlreadyPresentException ex) {
		JobPortalResponse<String> jobPortalResponse = JobPortalResponse.failure(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(jobPortalResponse);
	}

}
