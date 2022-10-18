package ua.foxminded.javaspring;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class StudentGenerator {
	
	static List<String> studentFirstNames = Arrays.asList("Lexi", "Elouise", "Wilbur", "Glenda", "Judah", "Salahuddin",
			"Juliet", "Tanner", "Luella", "Enid", "Hadiya", "Rares", "Bryan", "Patsy", "Eshan", "Lester", "Bentley",
			"Yu", "Finlay", "Sylvie");

	static List<String> studentLastNames = Arrays.asList("Ferry", "Buck", "Moody", "Craft", "Ridley", "Aguilar", "Garrett",
			"Peralta", "Mcknight", "O'Quinn", "Simons", "Kelley", "Trejo", "Dougherty", "Palacios", "Murphy", "Gordon",
			"Mcgee", "Strong", "Philip");
	
	Random random = new Random();
	
	void generateStudents() {
		
		StudentDAO studentDAO = new StudentDAO();
		IntStream.rangeClosed(1, 200).forEach(studentNumber -> studentDAO.addStudent(studentNumber,
				studentFirstNames.get(random.nextInt(20)), studentLastNames.get(random.nextInt(20))));
		System.out.println("Students created");
	}

}
