package com.floow.api.services.driverdetails.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.floow.api.dao.driverdetails.DriverDetailsDao;
import com.floow.api.exception.DriverDetailsApiException;
import com.floow.api.services.driverdetails.DriverService;
import com.floow.api.vo.DriverDetails;


@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	private DriverDetailsDao repo;

	@Override
	public DriverDetails create(DriverDetails details) throws DriverDetailsApiException {
		details.setId(UUID.randomUUID().toString());
		details.setCreationDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		return repo.create(details);
	}

	@Override
	public List<DriverDetails> findAllDrivers() throws DriverDetailsApiException {
		return repo.findAllDrivers();
	}

	@Override
	public List<DriverDetails> fetchDriversByDate(LocalDate fromDate) throws DriverDetailsApiException {
		return repo.fetchDriversByDate(fromDate);
	}


}
