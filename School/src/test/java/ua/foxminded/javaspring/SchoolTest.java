package ua.foxminded.javaspring;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SchoolTest {

	@Test
	void getFileLines_returnsLinesFromTestFile_testFile() {

		School school = new School();

		assertEquals("line1\n" + "line2\n" + "line3", school.readCreatingTablesScript("testFile.txt").toString());
	}
}
