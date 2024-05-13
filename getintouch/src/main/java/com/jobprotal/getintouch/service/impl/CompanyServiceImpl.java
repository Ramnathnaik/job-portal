package com.jobprotal.getintouch.service.impl;

import static com.jobprotal.getintouch.response.LoggerStatus.COMPLETED;
import static com.jobprotal.getintouch.response.LoggerStatus.STARTED;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobprotal.getintouch.entity.Candidate;
import com.jobprotal.getintouch.entity.Company;
import com.jobprotal.getintouch.exception.company.CandidateCannotRegisterCompanyException;
import com.jobprotal.getintouch.exception.company.CompanyAlreadyRegisteredException;
import com.jobprotal.getintouch.exception.company.CompanyNotFoundException;
import com.jobprotal.getintouch.helper.AppEnums.RoleType;
import com.jobprotal.getintouch.repository.CompanyRepository;
import com.jobprotal.getintouch.service.CompanyService;

import jakarta.transaction.Transactional;

@Service
public class CompanyServiceImpl implements CompanyService {
	private Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);
	
	@Autowired
	private CompanyRepository companyRepository;

	@Override
	@Transactional
	public Company registerCompany(Company company) {
		LOGGER.info("createCandidate Service: {}", STARTED);
		Optional<Company> foundCompany = this.companyRepository.findByName(company.getName());
		
		if (foundCompany.isPresent())
			throw new CompanyAlreadyRegisteredException("company already registered");
		
		Candidate candidate = company.getCandidate();
		
		if (candidate.getRole().equals(RoleType.CANDIDATE))
			throw new CandidateCannotRegisterCompanyException("candidate with CANDIDATE role cannot register a company");
		
		String companyId = UUID.randomUUID().toString();
		company.setCompanyId(companyId);
		
		LOGGER.info("createCandidate Service: {}", COMPLETED);
		return this.companyRepository.save(company);
	}

	@Override
	public List<Company> getCompanies() {
		return this.companyRepository.findAll();
	}

	@Override
	public Company getCompanyDetails(String companyId) {
		return this.companyRepository.findByCompanyId(companyId)
				.orElseThrow(() -> new CompanyNotFoundException("company not found with id: " + companyId));
	}

	@Override
	@Transactional
	public Company updateRegisteredCompany(Company company) {
		LOGGER.info("updateRegisteredCompany Service: {}", STARTED);
		Optional<Company> foundCompany = this.companyRepository.findByName(company.getName());
		
		if (!foundCompany.isPresent())
			throw new CompanyNotFoundException("company not found with id: " + company.getCompanyId());
		
		Candidate candidate = company.getCandidate();
		
		if (candidate.getRole().equals(RoleType.CANDIDATE))
			throw new CandidateCannotRegisterCompanyException("candidate with CANDIDATE role cannot update company details");
		
		LOGGER.info("updateRegisteredCompany Service: {}", COMPLETED);
		return this.companyRepository.save(company);
	}

	@Override
	@Transactional
	public void deleteRegisteredCompany(String companyId) {
		LOGGER.info("deleteRegisteredCompany Service: {}", STARTED);
		Optional<Company> foundCompany = this.companyRepository.findByCompanyId(companyId);
		
		if (!foundCompany.isPresent())
			throw new CompanyNotFoundException("company not found with id: " + companyId);
		
		Candidate candidate = foundCompany.get().getCandidate();
		
		if (candidate.getRole().equals(RoleType.CANDIDATE))
			throw new CandidateCannotRegisterCompanyException("candidate with CANDIDATE role cannot delete company details");
		
		this.companyRepository.deleteByCompanyId(companyId);
		LOGGER.info("deleteCandidate Service: {}", COMPLETED);
	}

}
