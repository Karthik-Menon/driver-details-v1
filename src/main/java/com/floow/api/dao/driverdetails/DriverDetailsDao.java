package com.floow.api.dao.driverdetails;

import java.time.LocalDate;
import java.util.List;

import com.floow.api.vo.DriverDetails;

public interface DriverDetailsDao {

	public DriverDetails create(DriverDetails details);
	
	public List<DriverDetails> findAllDrivers();
	
	public List<DriverDetails> fetchDriversByDate(LocalDate fromDate);


}
