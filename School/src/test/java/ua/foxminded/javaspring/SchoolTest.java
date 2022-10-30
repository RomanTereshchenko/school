package ua.foxminded.javaspring;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SchoolTest {

	@Test
	void readCreatingTablesScript_returnsLinesFromTestFile_testFile() {

		Service service = new Service();

		assertEquals("line1\n" + "line2\n" + "line3",
				service.readCreatingTablesScript("src/main/resources/testFile.txt").toString());
	}

	@Test
	void getProperty_returnsCorrectUrl_propertyName_url_() {

		assertEquals("jdbc:postgresql://localhost:5432/school", DBConfig.getProperty("url").toString());
	}

}
