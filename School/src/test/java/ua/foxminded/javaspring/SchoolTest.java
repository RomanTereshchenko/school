package ua.foxminded.javaspring;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SchoolTest {

	@Test
	void getFileLines_returnsLinesFromTestFile_testFile() {

		assertEquals("line1\n" + "line2\n" + "line3", Service.readCreatingTablesScript("testFile.txt").toString());
	}
}
