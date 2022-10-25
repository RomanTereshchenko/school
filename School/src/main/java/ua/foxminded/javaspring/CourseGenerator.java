package ua.foxminded.javaspring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseGenerator {
	
	private final List<String> courseNames = Arrays.asList("Mathematics", "Science", "Health", "Handwriting", "Art",
			"Music", "Leadership", "Speech", "English", "Algebra");

	List<Course> courses = generateCourses();
	
	List<Course> generateCourses(){
		List<Course> coursesLocal = new ArrayList<>();
		
		for (String courseName : courseNames) {
			coursesLocal.add(new Course(courseName));
		}
		System.out.println("Courses generated");
		return coursesLocal;
	}
	
}
