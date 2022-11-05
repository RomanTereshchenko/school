package ua.foxminded.javaspring;

import java.util.ArrayList;
import java.util.List;

public class CourseGenerator {

	List<Course> generateCourses() {
		Controller controller = new Controller();
		List<Course> coursesLocal = new ArrayList<>();

		for (int i = 0; i < controller.courseNames.size(); i++) {
			coursesLocal.add(new Course((i + 1), controller.courseNames.get(i)));
		}
		
		System.out.println("Courses generated");
		return coursesLocal;

	}

}
