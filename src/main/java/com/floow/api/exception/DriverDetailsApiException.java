package com.floow.api.exception;

public class DriverDetailsApiException extends RuntimeException {

	private static final long serialVersionUID = -2816795156596014007L;
	private String errorCode;

	public String getErrorCode() {
		return errorCode;
	}

	public DriverDetailsApiException() {
		super();
	}

	public DriverDetailsApiException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
	}

	public DriverDetailsApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public DriverDetailsApiException(String message) {
		super(message);
	}

	public DriverDetailsApiException(Throwable cause) {
		super(cause);
	}

}
