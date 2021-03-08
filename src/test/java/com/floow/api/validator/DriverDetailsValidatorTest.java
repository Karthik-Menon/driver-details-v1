package com.floow.api.validator;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.floow.api.exception.DriverDetailsApiException;
import com.floow.api.vo.DriverDetails;

@SpringBootTest
public class DriverDetailsValidatorTest {

	private DriverDetailsValidator validator;

	@BeforeEach
	public void setup() {
		validator = new DriverDetailsValidator();
	}

	@Test
	public void testValidationForValidFormat() {
		DriverDetails details = new DriverDetails();
		details.setDob("22-10-1993");
		details.setFirstName("Karthik");
		details.setLastName("Menon");
		details.setId(UUID.randomUUID().toString());
		details.setCreationDate("22-10-1993");

		Assertions.assertDoesNotThrow(() -> validator.validate(details, null));

	}

	@Test
	public void testValidationForInvalidDateFormat() {
		DriverDetails details = new DriverDetails();
		details.setDob("1993-22-10");
		details.setFirstName("Karthik");
		details.setLastName("Menon");
		details.setId(UUID.randomUUID().toString());
		details.setCreationDate("22-10-1993");

		Assertions.assertThrows(DriverDetailsApiException.class, () -> validator.validate(details, null));

	}

	@Test
	public void testValidationForInvalidDate() {
		DriverDetails details = new DriverDetails();
		details.setDob("123123123");
		details.setFirstName("Karthik");
		details.setLastName("Menon");
		details.setId(UUID.randomUUID().toString());
		details.setCreationDate("22-10-1993");

		Assertions.assertThrows(DriverDetailsApiException.class, () -> validator.validate(details, null));

	}

	@Test
	public void testValidationForDate() {
		DriverDetails details = new DriverDetails();
		details.setDob("22-10-19931");
		details.setFirstName("Karthik");
		details.setLastName("Menon");
		details.setId(UUID.randomUUID().toString());
		details.setCreationDate("22-10-1993");

		Assertions.assertThrows(DriverDetailsApiException.class, () -> validator.validate(details, null));

	}

}
