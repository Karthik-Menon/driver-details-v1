package com.floow.api.dao.driverdetails.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.floow.api.dao.driverdetails.DriverDetailsDao;
import com.floow.api.exception.DriverDetailsApiException;
import com.floow.api.util.FileUtil;
import com.floow.api.vo.DriverDetails;

@SpringBootTest
public class DriverDetailsDaoImplTest {

	DriverDetailsDao repo;

	@BeforeEach
	public void setup() {
		repo = new DriverDetailsDaoImpl();
		ReflectionTestUtils.setField(repo, "path", "Driver_Details.csv");
	}

	@Test
	public void createDriverRecord() {
		DriverDetails details = new DriverDetails();
		details.setDob("22-10-1993");
		details.setFirstName("Karthik");
		details.setLastName("Menon");
		details.setId(UUID.randomUUID().toString());
		details.setCreationDate("22-10-1993");
		try (MockedStatic<FileUtil> mocked = Mockito.mockStatic(FileUtil.class)) {
			mocked.when(() -> FileUtil.doesFileExist(Mockito.anyString())).thenReturn(false);
			repo.create(details);
			mocked.verify(() -> FileUtil.createAndWriteToFile(Mockito.anyList(), Mockito.anyString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createDriverDetailsTest() {
		DriverDetails details = new DriverDetails();
		details.setDob("22-10-1993");
		details.setFirstName("Karthik");
		details.setLastName("Menon");
		details.setId(UUID.randomUUID().toString());
		details.setCreationDate("22-10-1993");
		try (MockedStatic<FileUtil> mocked = Mockito.mockStatic(FileUtil.class)) {
			mocked.when(() -> FileUtil.doesFileExist(Mockito.anyString())).thenReturn(false);
			mocked.when(() -> FileUtil.createAndWriteToFile(Mockito.anyList(), Mockito.anyString()))
					.thenThrow(DriverDetailsApiException.class);
			Assertions.assertThrows(DriverDetailsApiException.class, () -> repo.create(details));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void appendTest() {
		DriverDetails details = new DriverDetails();
		details.setDob("22-10-1993");
		details.setFirstName("Karthik");
		details.setLastName("Menon");
		details.setId(UUID.randomUUID().toString());
		details.setCreationDate("22-10-1993");
		try (MockedStatic<FileUtil> mocked = Mockito.mockStatic(FileUtil.class)) {
			mocked.when(() -> FileUtil.doesFileExist(Mockito.anyString())).thenReturn(true);
			repo.create(details);
			mocked.verify(() -> FileUtil.appendToFile(Mockito.anyList(), Mockito.anyString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void appendExceptionTest() {
		DriverDetails details = new DriverDetails();
		details.setDob("22-10-1993");
		details.setFirstName("Karthik");
		details.setLastName("Menon");
		details.setId(UUID.randomUUID().toString());
		details.setCreationDate("22-10-1993");
		try (MockedStatic<FileUtil> mocked = Mockito.mockStatic(FileUtil.class)) {
			mocked.when(() -> FileUtil.doesFileExist(Mockito.anyString())).thenReturn(true);
			mocked.when(() -> FileUtil.appendToFile(Mockito.anyList(), Mockito.anyString()))
					.thenThrow(DriverDetailsApiException.class);
			Assertions.assertThrows(DriverDetailsApiException.class, () -> repo.create(details));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void findAllDrivers_testforvalidrecords() {
		List<Map<String, String>> recordList = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		map.put("CREATION_DATE", "08-03-2021");
		map.put("ID", "23ad966f-7e3b-48d1-bba4-eaeb9a728b61");
		map.put("LAST_NAME", "Manikuttan");
		map.put("DATE_OF_BIRTH", "08-03-2021");
		map.put("FIRST_NAME", "Archana");
		recordList.add(map);
		try (MockedStatic<FileUtil> mocked = Mockito.mockStatic(FileUtil.class)) {
			mocked.when(() -> FileUtil.doesFileExist(Mockito.anyString())).thenReturn(true);
			mocked.when(() -> FileUtil.getRecordsMapFromFile(Mockito.anyString())).thenReturn(recordList);
			assertEquals(1, repo.findAllDrivers().size());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void findAllDrivers_testforEmptyRecords() {
		List<Map<String, String>> recordList = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		map.put("CREATION_DATE", "08-03-2021");
		map.put("ID", "23ad966f-7e3b-48d1-bba4-eaeb9a728b61");
		map.put("LAST_NAME", "Manikuttan");
		map.put("DATE_OF_BIRTH", "08-03-2021");
		map.put("FIRST_NAME", "Archana");
		recordList.add(map);
		try (MockedStatic<FileUtil> mocked = Mockito.mockStatic(FileUtil.class)) {
			mocked.when(() -> FileUtil.doesFileExist(Mockito.anyString())).thenReturn(false);
			assertEquals(0, repo.findAllDrivers().size());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void findAllDrivers_testforException() {

		try (MockedStatic<FileUtil> mocked = Mockito.mockStatic(FileUtil.class)) {
			mocked.when(() -> FileUtil.doesFileExist(Mockito.anyString())).thenReturn(true);
			mocked.when(() -> FileUtil.getRecordsMapFromFile(Mockito.any())).thenThrow(DriverDetailsApiException.class);
			Assertions.assertThrows(DriverDetailsApiException.class, () -> repo.findAllDrivers());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void fetchdriversbydate_testforvalidrecords() {
		List<Map<String, String>> recordList = new ArrayList<>();
		Map<String, String> map1 = new HashMap<>();
		map1.put("CREATION_DATE", "08-03-2021");
		map1.put("ID", "23ad966f-7e3b-48d1-bba4-eaeb9a728b61");
		map1.put("LAST_NAME", "Manikuttan");
		map1.put("DATE_OF_BIRTH", "06-03-2021");
		map1.put("FIRST_NAME", "Archana");
		recordList.add(map1);
		Map<String, String> map2 = new HashMap<>();
		map2.put("CREATION_DATE", "02-03-2021");
		map2.put("ID", "23ad966f-7e3b-48d1-bba4-eaeb9a728b61");
		map2.put("LAST_NAME", "Manikuttan");
		map2.put("DATE_OF_BIRTH", "06-03-2021");
		map2.put("FIRST_NAME", "Archana");
		recordList.add(map2);

		try (MockedStatic<FileUtil> mocked = Mockito.mockStatic(FileUtil.class)) {
			mocked.when(() -> FileUtil.doesFileExist(Mockito.anyString())).thenReturn(true);
			mocked.when(() -> FileUtil.getRecordsMapFromFile(Mockito.anyString())).thenReturn(recordList);
			assertEquals(1, repo.fetchDriversByDate(LocalDate.of(2021, 03, 05)).size());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void fetchdriversbydate_testforempty() {
		List<Map<String, String>> recordList = new ArrayList<>();
		Map<String, String> map1 = new HashMap<>();
		map1.put("CREATION_DATE", "08-03-2021");
		map1.put("ID", "23ad966f-7e3b-48d1-bba4-eaeb9a728b61");
		map1.put("LAST_NAME", "Manikuttan");
		map1.put("DATE_OF_BIRTH", "06-03-2021");
		map1.put("FIRST_NAME", "Archana");
		recordList.add(map1);
		Map<String, String> map2 = new HashMap<>();
		map2.put("CREATION_DATE", "02-03-2021");
		map2.put("ID", "23ad966f-7e3b-48d1-bba4-eaeb9a728b61");
		map2.put("LAST_NAME", "Manikuttan");
		map2.put("DATE_OF_BIRTH", "06-03-2021");
		map2.put("FIRST_NAME", "Archana");
		recordList.add(map2);
		try (MockedStatic<FileUtil> mocked = Mockito.mockStatic(FileUtil.class)) {
			mocked.when(() -> FileUtil.doesFileExist(Mockito.anyString())).thenReturn(false);
			assertEquals(0, repo.fetchDriversByDate(LocalDate.of(2021, 03, 05)).size());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void finddriversbydate_testforException() {

		try (MockedStatic<FileUtil> mocked = Mockito.mockStatic(FileUtil.class)) {
			mocked.when(() -> FileUtil.doesFileExist(Mockito.anyString())).thenReturn(true);
			mocked.when(() -> FileUtil.getRecordsMapFromFile(Mockito.any())).thenThrow(DriverDetailsApiException.class);
			Assertions.assertThrows(DriverDetailsApiException.class,
					() -> repo.fetchDriversByDate(LocalDate.of(2021, 03, 05)));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
