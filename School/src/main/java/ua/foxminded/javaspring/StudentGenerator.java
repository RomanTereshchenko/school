package ua.foxminded.javaspring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class StudentGenerator {

	private GroupGenerator groupGenerator;
	private Random random = new Random();
	private int nextUnassignedStudentID = 1;

	private List<String> studentFirstNames = Arrays.asList("Lexi", "Elouise", "Wilbur", "Glenda", "Judah", "Salahuddin",
			"Juliet", "Tanner", "Luella", "Enid", "Hadiya", "Rares", "Bryan", "Patsy", "Eshan", "Lester", "Bentley",
			"Yu", "Finlay", "Sylvie");

	private List<String> studentLastNames = Arrays.asList("Ferry", "Buck", "Moody", "Craft", "Ridley", "Aguilar",
			"Garrett", "Peralta", "Mcknight", "O'Quinn", "Simons", "Kelley", "Trejo", "Dougherty", "Palacios", "Murphy",
			"Gordon", "Mcgee", "Strong", "Philip");

	List<Student> students = generateStudents();

	List<Student> generateStudents() {
		List<Student> studentsLocal = new ArrayList<>();
		for (int i = 0; i < 200; i++) {
			studentsLocal.add(
					new Student(studentFirstNames.get(random.nextInt(20)), studentLastNames.get(random.nextInt(20))));
		}
		System.out.println("Students generated");
		return studentsLocal;
	}

	void assignAllGroupsToAllItsStudents() {

		IntStream.rangeClosed(1, groupGenerator.groups.size())
				.forEach(groupID -> assignOneGroupToAllItsStudents(groupID));
		System.out.println("Students assigned with groups");
	}

	private void assignOneGroupToAllItsStudents(int groupID) {

		int limitOfStudentsInGroup = ((random.nextInt(20)) + 10);
		int numberOfStudentsInGroup = 0;

		while (numberOfStudentsInGroup < limitOfStudentsInGroup) {
			assignGroupToStudent(groupID, nextUnassignedStudentID);
			numberOfStudentsInGroup++;
			nextUnassignedStudentID++;
		}

	}

	private void assignGroupToStudent(int groupID, int studentID) {

		students.get(studentID).setGroupID(groupID);

	}

}
