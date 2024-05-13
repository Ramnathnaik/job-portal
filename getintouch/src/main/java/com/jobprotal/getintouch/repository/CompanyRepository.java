package com.jobprotal.getintouch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobprotal.getintouch.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, String>{
	
	Optional<Company> findByName(String name);
	
	Optional<Company> findByCompanyId(String companyId);
	
	void deleteByCompanyId(String companyId);

}
