package com.floow.api.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.floow.api.exception.DriverDetailsApiException;
import com.floow.api.vo.DriverDetails;

@Component
public class DriverDetailsValidator implements Validator {
	
	private static final Logger logger = LoggerFactory.getLogger(DriverDetailsValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return DriverDetails.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		try {
			DriverDetails details = (DriverDetails) target;
			validateDateOfBirth(details.getDob());
		} catch (Exception e) {
			throw e;
		}
	}

	private void validateDateOfBirth(String dob) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate.parse(dob, formatter);
		} catch (Exception e) {
			logger.error("Exception in validateDateOfBirth method", e);
			throw new DriverDetailsApiException("Invalid date format. Expecting dd-MM-yyyy format.");
		}

	}

}
