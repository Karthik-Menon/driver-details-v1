package com.floow.api.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.floow.api.services.driverdetails.DriverService;
import com.floow.api.validator.DriverDetailsValidator;
import com.floow.api.vo.DriverDetails;

@RestController
@Validated
public class DriverDetailsController {

	@Autowired
	private DriverService service;

	@Autowired
	private DriverDetailsValidator validator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validator);
	}

	@PostMapping(path = "/driver/create", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> createDriver(@RequestBody @Valid DriverDetails details, BindingResult errors) {
		return ResponseEntity.ok(service.create(details));
	}

	@GetMapping(path = "/drivers", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> fetchAllDrivers() {
		return ResponseEntity.ok(service.findAllDrivers());
	}

	@GetMapping(path = "/drivers/byDate", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Object> fetchDriversByDate(
			@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd-MM-yyyy") LocalDate date) {
		return ResponseEntity.ok(service.fetchDriversByDate(date));
	}

}
