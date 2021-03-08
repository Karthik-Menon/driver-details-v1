package com.floow.api.exception;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionsHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
		List<String> details = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
		return new ResponseEntity<>(new ErrorResponse("FAILED", HttpStatus.BAD_REQUEST.name(), details),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DriverDetailsApiException.class)
	protected ResponseEntity<Object> handleDriverDetailsApiException(DriverDetailsApiException ex) {
		return new ResponseEntity<>(
				new ErrorResponse("FAILED", HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleDateTimeParseException(MethodArgumentTypeMismatchException e) {
		String message = String.format("'%s' should be a valid '%s' and '%s' is not a valid type.", e.getName(), e.getRequiredType().getSimpleName(), e.getValue());
		return new ResponseEntity<>(new ErrorResponse("FAILED", HttpStatus.BAD_REQUEST.name(), message),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<Object> handleMissingParam(MissingServletRequestParameterException ex) {
		return new ResponseEntity<>(new ErrorResponse("FAILED", HttpStatus.BAD_REQUEST.name(),
				String.format("'%s' parameter is mandatory.", ex.getParameterName())), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGenericException(Exception ex){
		return new ResponseEntity<>(new ErrorResponse("FAILED", HttpStatus.INTERNAL_SERVER_ERROR.name(),
				ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
