package com.floow.api.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.floow.api.exception.DriverDetailsApiException;
import com.floow.api.vo.DriverDetails;

@SpringBootTest
@AutoConfigureMockMvc
public class DriverDetailsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void createDriverInvalidDateTest() throws Exception {
		DriverDetails details = new DriverDetails();
		details.setDob("22-10-19932");
		details.setFirstName("Karthik");
		details.setLastName("Menon");
		this.mockMvc
				.perform(post("/driver/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(details)))
				.andExpect(
						response -> assertTrue(response.getResolvedException() instanceof DriverDetailsApiException));
	}

	@Test
	public void createDriverViolateLastNameConstraint() throws Exception {
		DriverDetails details = new DriverDetails();
		details.setDob("22-10-1993");
		details.setFirstName("Karthik");
		this.mockMvc
				.perform(post("/driver/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(details)))
				.andExpect(response -> assertTrue(
						response.getResolvedException() instanceof ConstraintViolationException));
	}

	@Test
	public void createDriverViolateDOBConstraint() throws Exception {
		DriverDetails details = new DriverDetails();
		details.setFirstName("Karthik");
		details.setLastName("Menon");
		this.mockMvc
				.perform(post("/driver/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(details)))
				.andExpect(
						response -> assertTrue(response.getResolvedException() instanceof DriverDetailsApiException));
	}

	@Test
	public void fetchDriversByDateInvalidRequest() throws Exception {

		this.mockMvc.perform(get("/drivers/byDate?date=220-10-2123").contentType(MediaType.APPLICATION_JSON)).andExpect(
				response -> assertTrue(response.getResolvedException() instanceof MethodArgumentTypeMismatchException));
	}

	@Test
	public void fetchDriversByDateInvalidDateFormat() throws Exception {

		this.mockMvc.perform(get("/drivers/byDate?date=15-15-1889").contentType(MediaType.APPLICATION_JSON)).andExpect(
				response -> assertTrue(response.getResolvedException() instanceof MethodArgumentTypeMismatchException));
	}

	@Test
	public void fetchDriversByDateInvalidDateFormatYYYYmmdd() throws Exception {

		this.mockMvc.perform(get("/drivers/byDate?date=1889-12-01").contentType(MediaType.APPLICATION_JSON)).andExpect(
				response -> assertTrue(response.getResolvedException() instanceof MethodArgumentTypeMismatchException));
	}

	@Test
	public void fetchDriversByDateEmptyRequestDate() throws Exception {

		this.mockMvc.perform(get("/drivers/byDate").contentType(MediaType.APPLICATION_JSON))
				.andExpect(response -> assertTrue(
						response.getResolvedException() instanceof MissingServletRequestParameterException));
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
