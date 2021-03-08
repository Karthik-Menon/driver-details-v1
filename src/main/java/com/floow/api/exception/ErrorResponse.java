package com.floow.api.exception;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {
	private String status;
	private String error;
	private String errorMessage;
	private String description;
	private List<String> details;

	public ErrorResponse(String status, String error, List<String> details) {
		super();
		this.status = status;
		this.error = error;
		this.details = details;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String status,String description) {
		this.status = status;
		this.description = description;
	}

	public ErrorResponse(String status , String error, String errorMessage) {
		super();
		this.error = error;
		this.errorMessage = errorMessage;
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
