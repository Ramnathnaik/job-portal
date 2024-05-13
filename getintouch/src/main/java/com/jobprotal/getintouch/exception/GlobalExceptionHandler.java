package com.jobprotal.getintouch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jobprotal.getintouch.exception.candidate.CandidateAlreadyPresentException;
import com.jobprotal.getintouch.exception.candidate.CandidateNotFoundException;
import com.jobprotal.getintouch.exception.company.CandidateCannotRegisterCompanyException;
import com.jobprotal.getintouch.exception.company.CompanyAlreadyRegisteredException;
import com.jobprotal.getintouch.exception.company.CompanyNotFoundException;
import com.jobprotal.getintouch.exception.job.JobNotFoundException;
import com.jobprotal.getintouch.response.JobPortalResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({CandidateNotFoundException.class})
	public ResponseEntity<JobPortalResponse<String>> handleCandidateNotFoundException(CandidateNotFoundException ex) {
		JobPortalResponse<String> jobPortalResponse = JobPortalResponse.failure(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jobPortalResponse);
	}
	
	@ExceptionHandler({CandidateAlreadyPresentException.class})
	public ResponseEntity<JobPortalResponse<String>> handleCandidateAlreadyPresentException(CandidateAlreadyPresentException ex) {
		JobPortalResponse<String> jobPortalResponse = JobPortalResponse.failure(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(jobPortalResponse);
	}
	
	@ExceptionHandler({CompanyAlreadyRegisteredException.class})
	public ResponseEntity<JobPortalResponse<String>> handleCompanyAlreadyRegisteredException(CompanyAlreadyRegisteredException ex) {
		JobPortalResponse<String> jobPortalResponse = JobPortalResponse.failure(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(jobPortalResponse);
	}
	
	@ExceptionHandler({CandidateCannotRegisterCompanyException.class})
	public ResponseEntity<JobPortalResponse<String>> handleCompanyAlreadyRegisteredException(CandidateCannotRegisterCompanyException ex) {
		JobPortalResponse<String> jobPortalResponse = JobPortalResponse.failure(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jobPortalResponse);
	}
	
	@ExceptionHandler({CompanyNotFoundException.class})
	public ResponseEntity<JobPortalResponse<String>> handleCompanyNotFoundException(CompanyNotFoundException ex) {
		JobPortalResponse<String> jobPortalResponse = JobPortalResponse.failure(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jobPortalResponse);
	}
	
	@ExceptionHandler({JobNotFoundException.class})
	public ResponseEntity<JobPortalResponse<String>> handleJobNotFoundException(JobNotFoundException ex) {
		JobPortalResponse<String> jobPortalResponse = JobPortalResponse.failure(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jobPortalResponse);
	}
	
	@ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
		ProblemDetail errorDetail = null;

        // TODO send this stack trace to an observability tool
        exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The username or password is incorrect");

            return errorDetail;
        }

        if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The account is locked");
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "You are not authorized to access this resource");
        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT signature is invalid");
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT token has expired");
        }

        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
            errorDetail.setProperty("description", "Unknown internal server error.");
        }

        return errorDetail;

	}

}
