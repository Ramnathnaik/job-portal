package com.jobprotal.getintouch.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobprotal.getintouch.entity.Job;
import com.jobprotal.getintouch.exception.job.JobNotFoundException;
import com.jobprotal.getintouch.repository.JobRepository;
import com.jobprotal.getintouch.service.JobService;

import jakarta.transaction.Transactional;

import static com.jobprotal.getintouch.response.LoggerStatus.STARTED;
import static com.jobprotal.getintouch.response.LoggerStatus.COMPLETED;

@Service
public class JobServiceImpl implements JobService {
	
	private Logger LOGGER = LoggerFactory.getLogger(JobServiceImpl.class);
	
	@Autowired
	private JobRepository jobRepository;

	@Override
	@Transactional
	public Job createJob(Job job) {
		LOGGER.info("createJob Service: {}", STARTED);
		String jobId = UUID.randomUUID().toString();
		job.setJobId(jobId);
		LOGGER.info("createJob Service: {}", COMPLETED);
		return this.jobRepository.save(job);
	}

	@Override
	public Job getJobDetails(String jobId) {
		return this.jobRepository.findByJobId(jobId).orElseThrow(() -> new JobNotFoundException("job not found for id: " + jobId));
	}

	@Override
	public List<Job> getJobs() {
		return this.jobRepository.findAll();
	}

	@Override
	@Transactional
	public Job updateJob(Job job) {
		Optional<Job> foundJob = this.jobRepository.findByJobId(job.getJobId());
		
		if (!foundJob.isPresent())
			throw new JobNotFoundException("job not found for id: " + job.getJobId());
		
		return this.jobRepository.save(job);
	}

	@Override
	@Transactional
	public void deleteJob(String jobId) {
		Optional<Job> foundJob = this.jobRepository.findByJobId(jobId);
		
		if (!foundJob.isPresent())
			throw new JobNotFoundException("job not found for id: " + jobId);
		
		this.jobRepository.deleteByJobId(jobId);
		
	}

}
