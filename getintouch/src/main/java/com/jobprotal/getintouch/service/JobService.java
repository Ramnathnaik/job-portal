package com.jobprotal.getintouch.service;

import java.util.List;

import com.jobprotal.getintouch.entity.Job;

public interface JobService {
	
	Job createJob(Job job);
	
	Job getJobDetails(String jobId);
	
	List<Job> getJobs();
	
	Job updateJob(Job job);
	
	void deleteJob(String jobId);

}
