package com.jobprotal.getintouch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobprotal.getintouch.entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
	
	Optional<Candidate> findByUsername(String username);

}
