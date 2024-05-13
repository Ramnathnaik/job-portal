package com.jobprotal.getintouch.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobprotal.getintouch.entity.Candidate;
import com.jobprotal.getintouch.exception.candidate.CandidateAlreadyPresentException;
import com.jobprotal.getintouch.exception.candidate.CandidateNotFoundException;
import com.jobprotal.getintouch.repository.CandidateRepository;
import com.jobprotal.getintouch.service.CandidateService;

import jakarta.transaction.Transactional;

import static com.jobprotal.getintouch.response.LoggerStatus.STARTED;
import static com.jobprotal.getintouch.response.LoggerStatus.COMPLETED;

@Service
public class CandidateServiceImpl implements CandidateService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CandidateServiceImpl.class);
	
	@Autowired
	private CandidateRepository candidateRepository;

	@Override
	public Candidate getCandidate(String id) {
		return this.candidateRepository.findByCandidateId(id)
				.orElseThrow(() -> new CandidateNotFoundException("candidate not found with id: " + id));
	}

	@Override
	@Transactional
	public Candidate updateCandidate(Candidate candidate) {
		LOGGER.info("updateCandidate Service: {}", STARTED);
		Optional<Candidate> presentCandidate = this.candidateRepository.findByUsername(candidate.getUsername());
		
		if (!presentCandidate.isPresent())
			throw new CandidateAlreadyPresentException("candidate not found with id: " + candidate.getUsername());
		
		LOGGER.info("updateCandidate Service: {}", COMPLETED);
		return this.candidateRepository.save(candidate);
	}

	@Override
	@Transactional
	public void deleteCandidate(String id) {
		LOGGER.info("deleteCandidate Service: {}", STARTED);
		Optional<Candidate> candidate = this.candidateRepository.findByCandidateId(id);
		
		if (!candidate.isPresent())
			throw new CandidateNotFoundException("candidate not found with id: " + id);
		
		this.candidateRepository.deleteByCandidateId(id);
		LOGGER.info("deleteCandidate Service: {}", COMPLETED);
	}

	@Override
	public List<Candidate> getAllCandidates() {
		return this.candidateRepository.findAll();
	}

}
