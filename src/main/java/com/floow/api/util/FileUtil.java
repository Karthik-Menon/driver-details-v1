package com.floow.api.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.floow.api.enums.Headers;
import com.floow.api.exception.DriverDetailsApiException;

public class FileUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static void createAndWriteToFile(List<String> values, String path) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path), true));
				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(Headers.class));) {
			csvPrinter.printRecord(values);
			csvPrinter.flush();
		} catch (Exception e) {
			logger.error("Exception in createAndWriteToFile method", e);
			throw new DriverDetailsApiException("Unable to process your request right now. Please try again later.");
		}
	}

	public static void appendToFile(List<String> values, String path) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path), true));
				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);) {
			csvPrinter.printRecord(values);
			csvPrinter.flush();
		} catch (Exception e) {
			logger.error("Exception in appendToFile method", e);
			throw new DriverDetailsApiException("Unable to process your request right now. Please try again later.");
		}

	}

	public static boolean doesFileExist(String path) {
		try {
			File file = new File(path);
			return file.exists();
		} catch (Exception e) {
			logger.error(path, e);
			throw new DriverDetailsApiException("Unable to process your request right now. Please try again later.");
		}
	}

	public static List<Map<String, String>> getRecordsMapFromFile(String path) {
		try {
			CSVParser parser = CSVParser.parse(Paths.get(path), Charset.defaultCharset(),
					CSVFormat.DEFAULT.withHeader(Headers.class));
			List<Map<String, String>> recordList = StreamSupport.stream(parser.spliterator(), false).skip(1)
					.map(CSVRecord::toMap).collect(Collectors.toList());
			return recordList;
		} catch (Exception e) {
			logger.error(path, e);
			throw new DriverDetailsApiException("Unable to process your request right now. Please try again later.");
		}

	}
}
