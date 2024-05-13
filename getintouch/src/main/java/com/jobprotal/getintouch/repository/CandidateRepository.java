package com.jobprotal.getintouch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobprotal.getintouch.entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, String> {
	
	Optional<Candidate> findByCandidateId(String id);
	
	Optional<Candidate> findByUsername(String username);
	
	void deleteByCandidateId(String id);

}
