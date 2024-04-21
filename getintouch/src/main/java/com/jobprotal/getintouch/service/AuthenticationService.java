package com.jobprotal.getintouch.service;

import com.jobprotal.getintouch.entity.Candidate;
import com.jobprotal.getintouch.model.LoginRequest;

public interface AuthenticationService {

	Candidate signup(Candidate candidate);
	
	Candidate authenticate(LoginRequest login);
}
