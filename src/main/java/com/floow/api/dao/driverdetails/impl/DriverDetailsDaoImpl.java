package com.floow.api.dao.driverdetails.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.floow.api.dao.driverdetails.DriverDetailsDao;
import com.floow.api.enums.Headers;
import com.floow.api.exception.DriverDetailsApiException;
import com.floow.api.util.FileUtil;
import com.floow.api.vo.DriverDetails;

@Repository
public class DriverDetailsDaoImpl implements DriverDetailsDao {

	private static final Logger logger = LoggerFactory.getLogger(DriverDetailsDaoImpl.class);

	@Value("${driver.detail.file.path}")
	private String path;

	@Override
	public DriverDetails create(DriverDetails details) throws DriverDetailsApiException {
		if (!FileUtil.doesFileExist(path)) {
			FileUtil.createAndWriteToFile(convertToIterable(details), path);
		} else {
			FileUtil.appendToFile(convertToIterable(details), path);
		}
		return details;

	}

	@Override
	public List<DriverDetails> findAllDrivers() {
		try {
			if (FileUtil.doesFileExist(path)) {
				List<Map<String, String>> recordList = FileUtil.getRecordsMapFromFile(path);
				return recordList.stream().map(toDriverDetails()).collect(Collectors.toList());
			}
		} catch (Exception e) {
			logger.error("Exception in findAllDrivers method", e);
			throw new DriverDetailsApiException("Unable to process your request right now. Please try again later.");
		}
		return new ArrayList<>();
	}

	@Override
	public List<DriverDetails> fetchDriversByDate(LocalDate givenDate) {
		try {
			if (FileUtil.doesFileExist(path)) {
				List<Map<String, String>> recordList = FileUtil.getRecordsMapFromFile(path);
				return recordList.stream().map(toDriverDetails()).filter(datesAfterGivenDate(givenDate))
						.collect(Collectors.toList());
			}
		} catch (Exception e) {
			logger.error("Exception in fetchDriversByDate method", e);
			throw new DriverDetailsApiException("Unable to process your request right now. Please try again later.");
		}
		return new ArrayList<>();
	}

	public static Predicate<DriverDetails> datesAfterGivenDate(LocalDate givenDate) {
		return (driver) -> LocalDate.parse(driver.getCreationDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))
				.isAfter(givenDate);
	}

	private List<String> convertToIterable(DriverDetails details) {
		List<String> rowValues = new ArrayList<String>();
		rowValues.add(details.getId());
		rowValues.add(details.getFirstName());
		rowValues.add(details.getLastName());
		rowValues.add(details.getDob());
		rowValues.add(details.getCreationDate());
		return rowValues;
	}

	private static Function<Map<String, String>, DriverDetails> toDriverDetails() {
		return (row) -> new DriverDetails(row.get(Headers.ID.name()), row.get(Headers.FIRST_NAME.name()),
				row.get(Headers.LAST_NAME.name()), row.get(Headers.DATE_OF_BIRTH.name()),
				row.get(Headers.CREATION_DATE.name()));
	}

}
