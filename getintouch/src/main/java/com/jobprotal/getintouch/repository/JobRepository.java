package com.jobprotal.getintouch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobprotal.getintouch.entity.Job;

public interface JobRepository extends JpaRepository<Job, String> {
	
	Optional<Job> findByJobId(String jobId);
	
	void deleteByJobId(String jobId);

}
