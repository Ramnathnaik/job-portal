package com.jobprotal.getintouch.response;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Component
public class JobPortalResponse<T> {

	@JsonSerialize
	private Status status;
	private boolean failed;
	private T data;
	private String message;
	private Instant timestamp;
	
	public static JobPortalResponse build() {
		return new JobPortalResponse().setTimestamp(Instant.now());
	}
	
	public static <T> JobPortalResponse success(T data, String message) {
		return build().setData(data).setStatus(Status.SUCCESS).setMessage(message);
	}
	
	public static <T> JobPortalResponse success(T data) {
		return build().setData(data).setStatus(Status.SUCCESS);
	}
	
	public static <T> JobPortalResponse success(String message) {
		return build().setStatus(Status.SUCCESS).setMessage(message);
	}
	
	public static <T> JobPortalResponse failure(String errorMessage) {
		return build().setStatus(Status.FAILED).setMessage(errorMessage);
	}
	
	public static <T> JobPortalResponse failure(T data, String errorMessage) {
		return build().setData(data).setStatus(Status.FAILED).setMessage(errorMessage);
	}
	
	public boolean isFailed() {
		return this.status == Status.FAILED;
	}
	
	public Status getStatus() {
		return status;
	}
	public JobPortalResponse<T> setStatus(Status status) {
		this.status = status;
		return this;
	}
	public void setFailed(boolean failed) {
		this.failed = failed;
	}
	public T getData() {
		return data;
	}
	public JobPortalResponse<T> setData(T data) {
		this.data = data;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public JobPortalResponse<T> setMessage(String message) {
		this.message = message;
		return this;
	}
	public Instant getTimestamp() {
		return timestamp;
	}
	public JobPortalResponse<T> setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	@Override
	public String toString() {
		return "JobPortalResponse [status=" + status + ", failed=" + failed + ", data=" + data + ", message=" + message
				+ ", timestamp=" + timestamp + "]";
	}
	
	public static enum Status {
		SUCCESS, FAILED;
	}
	
}
