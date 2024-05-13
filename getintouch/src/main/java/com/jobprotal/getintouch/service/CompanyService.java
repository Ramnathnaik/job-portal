package com.jobprotal.getintouch.service;

import java.util.List;

import com.jobprotal.getintouch.entity.Company;

public interface CompanyService {
	
	Company registerCompany(Company company);
	
	List<Company> getCompanies();
	
	Company getCompanyDetails(String companyId);
	
	Company updateRegisteredCompany(Company company);
	
	void deleteRegisteredCompany(String companyId);

}
