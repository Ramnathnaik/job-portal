package com.jobprotal.getintouch.service.impl;

import static com.jobprotal.getintouch.response.LoggerStatus.COMPLETED;
import static com.jobprotal.getintouch.response.LoggerStatus.STARTED;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobprotal.getintouch.entity.Candidate;
import com.jobprotal.getintouch.exception.candidate.CandidateAlreadyPresentException;
import com.jobprotal.getintouch.model.LoginRequest;
import com.jobprotal.getintouch.repository.CandidateRepository;
import com.jobprotal.getintouch.service.AuthenticationService;

import jakarta.transaction.Transactional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	
	@Autowired
	private CandidateRepository candidateRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	@Transactional
	public Candidate signup(Candidate candidate) {
		LOGGER.info("createCandidate Service: {}", STARTED);
		System.out.println("Candidate:: " + candidate);
		Optional<Candidate> presentCandidate = this.candidateRepository.findByUsername(candidate.getUsername());
		
		if (presentCandidate.isPresent())
			throw new CandidateAlreadyPresentException("candidate already exists");
		
		String hashedPassword = passwordEncoder.encode(candidate.getPassword());
		candidate.setPassword(hashedPassword);
		
		String candidateID = UUID.randomUUID().toString();
		candidate.setCandidateId(candidateID);
		
		LOGGER.info("createCandidate Service: {}", COMPLETED);
		return this.candidateRepository.save(candidate);
	}

	@Override
	public Candidate authenticate(LoginRequest login) {
		authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		login.getUsername(),
                		login.getPassword()
                )
        );

        return this.candidateRepository.findByUsername(login.getUsername())
                .orElseThrow();
	}
	
	@Override
	public boolean isUsernamePresent(String username) {
		Optional<Candidate> foundCandidate = this.candidateRepository.findByUsername(username);
		return foundCandidate.isPresent() ? true : false;
	}

}
