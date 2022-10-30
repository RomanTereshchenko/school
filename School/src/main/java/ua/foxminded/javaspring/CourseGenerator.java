package ua.foxminded.javaspring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseGenerator {

	private final List<String> courseNames = Arrays.asList("Mathematics", "Science", "Health", "Handwriting", "Art",
			"Music", "Leadership", "Speech", "English", "Algebra");

	static List<Course> courses = new ArrayList<>();

	void generateCourses() {

		for (int i = 0; i < courseNames.size(); i++) {
			courses.add(new Course((i + 1), courseNames.get(i)));
		}
		
		System.out.println("Courses generated");

	}

}
