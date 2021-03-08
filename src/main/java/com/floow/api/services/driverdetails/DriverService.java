package com.floow.api.services.driverdetails;

import java.time.LocalDate;
import java.util.List;

import com.floow.api.exception.DriverDetailsApiException;
import com.floow.api.vo.DriverDetails;

public interface DriverService {
	public DriverDetails create(DriverDetails details) throws DriverDetailsApiException;

	public List<DriverDetails> findAllDrivers() throws DriverDetailsApiException;

	public List<DriverDetails> fetchDriversByDate(LocalDate fromDate) throws DriverDetailsApiException;

}
