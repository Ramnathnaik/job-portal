package com.jobprotal.getintouch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jobprotal.getintouch.entity.Candidate;

@Service
public interface CandidateService {
	
	Candidate getCandidate(String id);
	
	Candidate updateCandidate(Candidate candidate);
	
	void deleteCandidate(String id);
	
	List<Candidate> getAllCandidates();

}
