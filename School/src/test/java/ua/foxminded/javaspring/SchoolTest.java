package ua.foxminded.javaspring;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

class SchoolTest {
	
	Random random = new Random();

	@Test
	void readCreatingTablesScript_returnsLinesFromTestFile_testFile() {

		Service service = new Service();

		assertEquals("line1\n" + "line2\n" + "line3",
				service.readCreatingTablesScript("src/test/resources/testFile.txt"));
	}

	@Test
	void getProperty_returnsCorrectUrl_propertyName_url_() {

		assertEquals("jdbc:postgresql://localhost:5432/school", DBConfig.getProperty("url"));
	}
	
	@Test
	void generateNgroups_returnsRequiredNumberOfGroups_anyIntN() {
		
		GroupGenerator groupGenerator = new GroupGenerator();
		
		int anyIntN = random.nextInt(10);
		
		assertEquals(anyIntN, groupGenerator.generateNGroups(anyIntN).get(anyIntN - 1).getGroupID());
	}
	
	@Test
	void generateCourses_returnsCoursesContainingWithRequiredName_anyCourseNameFromList() {
		
		CourseGenerator courseGenerator = new CourseGenerator();
		List<Course> testCourses = courseGenerator.generateCourses();
		List<String> testCoursesNames = new ArrayList<>();
		for (Course course : testCourses) {
			testCoursesNames.add(course.getCourseName());
		}
		
		String anyCourseNameFromList = "Mathematics";
		assertTrue(testCoursesNames.contains(anyCourseNameFromList));
	}
	
	@Test
	void generateStudents_returnsRequiredNumberOfStudents_anyIntN() {
		
		StudentGenerator studentGenerator = new StudentGenerator();
		
		int anyIntN = 5;
		assertEquals(anyIntN, studentGenerator.generateNStudents(anyIntN).get(anyIntN - 1).getStudentID());
	}

}
