package com.jobprotal.getintouch.controller;

import static com.jobprotal.getintouch.response.LoggerStatus.COMPLETED;
import static com.jobprotal.getintouch.response.LoggerStatus.STARTED;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobprotal.getintouch.config.JwtService;
import com.jobprotal.getintouch.entity.Candidate;
import com.jobprotal.getintouch.model.LoginRequest;
import com.jobprotal.getintouch.model.LoginResponse;
import com.jobprotal.getintouch.response.JobPortalResponse;
import com.jobprotal.getintouch.service.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	private final JwtService jwtService;
    
    private final AuthenticationService authenticationService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }
    
    @PostMapping("/signup")
	public ResponseEntity<JobPortalResponse<?>> signup(@Valid @RequestBody Candidate candidate,
			BindingResult bindingResult) {
		LOGGER.info("createCandidate: {}", STARTED);
		if (bindingResult.hasErrors()) {
			Map<String, String> validationErrors = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				validationErrors.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(JobPortalResponse.failure(validationErrors, "validation errors"));
		}
		
		Candidate newCandidate = this.authenticationService.signup(candidate);
		JobPortalResponse<Candidate> jobPortalResponse = JobPortalResponse.success(newCandidate,
				"candidate created successfully");
		LOGGER.info("createCandidate: {}", COMPLETED);
		return new ResponseEntity(jobPortalResponse, HttpStatus.CREATED);
	}
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    	Candidate authenticatedCandidate = this.authenticationService.authenticate(loginRequest);
    	
    	String jwtToken = jwtService.generateToken(authenticatedCandidate);
    	
    	LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());
    	
    	return ResponseEntity.ok(loginResponse);
    }
    
    @GetMapping("/{username}")
	public ResponseEntity<JobPortalResponse<?>> isUsernamePresent(@PathVariable String username) {
		LOGGER.info("isUsernamePresent: {}", STARTED);
		if (username.length() <= 3)
			return ResponseEntity.badRequest().body(JobPortalResponse.failure("Username must be greater than 3 characters"));
		
		boolean isUsernamePresent = this.authenticationService.isUsernamePresent(username);
		JobPortalResponse jobPortalResponse = JobPortalResponse.success(isUsernamePresent);
		LOGGER.info("isUsernamePresent: {}", COMPLETED);
		return ResponseEntity.ok(jobPortalResponse);
	}
    
}
