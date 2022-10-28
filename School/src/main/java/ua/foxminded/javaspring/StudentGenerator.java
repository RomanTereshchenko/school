package ua.foxminded.javaspring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class StudentGenerator {

	private GroupGenerator groupGenerator = new GroupGenerator();
	private CourseGenerator courseGenerator = new CourseGenerator();
	private Random random = new Random();
	private int nextUnassignedStudentID = 0;

	private List<String> studentFirstNames = Arrays.asList("Lexi", "Elouise", "Wilbur", "Glenda", "Judah", "Salahuddin",
			"Juliet", "Tanner", "Luella", "Enid", "Hadiya", "Rares", "Bryan", "Patsy", "Eshan", "Lester", "Bentley",
			"Yu", "Finlay", "Sylvie");

	List<String> studentLastNames = Arrays.asList("Ferry", "Buck", "Moody", "Craft", "Ridley", "Aguilar",
			"Garrett", "Peralta", "Mcknight", "O'Quinn", "Simons", "Kelley", "Trejo", "Dougherty", "Palacios", "Murphy",
			"Gordon", "Mcgee", "Strong", "Philip");

	List<Student> students = new ArrayList<>();
		
	List<Student> generateStudents() {
		
		IntStream.rangeClosed(1, 200)
		.forEach(studentID -> students
				.add(new Student(studentID, studentFirstNames.get(random.nextInt(20)), studentLastNames.get(random.nextInt(20)))));

		System.out.println("Students generated");
		System.out.println(students);
		return students;
	}

	void assignAllGroupsToAllItsStudents() {
		while (nextUnassignedStudentID < students.size()) {
			IntStream.rangeClosed(1, groupGenerator.groups.size()).forEach(this::assignOneGroupToAllItsStudents);
		}
		System.out.println("Students assigned with groups");
	}

	private void assignOneGroupToAllItsStudents(int groupID) {

		int limitOfStudentsInGroup = ((random.nextInt(20)) + 10);
		int numberOfStudentsInGroup = 0;

		while ((numberOfStudentsInGroup < limitOfStudentsInGroup) && (nextUnassignedStudentID < students.size())) {
			assignGroupToStudent(groupID, nextUnassignedStudentID);
			numberOfStudentsInGroup++;
			nextUnassignedStudentID++;
		}

	}

	private void assignGroupToStudent(int groupID, int studentIndex) {

		students.get(studentIndex).setGroupID(groupID);
		System.out.println("Student " + studentIndex + " assigned to group " + groupID);

	}

	void assignCoursesToAllStudents() {

		IntStream.rangeClosed(0, (students.size() - 1)).forEach(this::assignCoursesToOneStudent);
		System.out.println("Students assigned with lists of courses");
	}

	private void assignCoursesToOneStudent(int nextStudentID) {

		int numberOfCoursesLimit = (random.nextInt(2) + 1);
		int numberOfAssignedCourses = 1;

		ArrayList<Integer> courseIDs = new ArrayList<>();
		for (int i = 0; i < courseGenerator.courses.size(); i++)
			courseIDs.add(i);
		Collections.shuffle(courseIDs);
		int randomCourseIDIndex = 0;

		List<Course> coursesOfStudent = new ArrayList<>();
		while (numberOfAssignedCourses <= numberOfCoursesLimit) {
			coursesOfStudent.add(courseGenerator.courses.get(courseIDs.get(randomCourseIDIndex)));
			randomCourseIDIndex++;
			numberOfAssignedCourses++;
		}
		System.out.println(students);
		students.get(nextStudentID).setCourses(coursesOfStudent);
	}

}
