package ua.foxminded.javaspring;

import java.util.Scanner;

public class Controller {
	
	static StudentDAO studentDAO = new StudentDAO();
	static GroupDAO groupDAO = new GroupDAO();
	
	static void menu() {
		

		Scanner scan = new Scanner(System.in);

		findGroupsWithLessOrEqualStudentsNum(scan);
		findStudentsRelatedToCourse(scan);
		addNewStudent(scan);
		deleteStudent(scan);
		addStudentToCourse(scan);
		removeStudentFromCourse(scan);

		scan.close();

	}

	private static void removeStudentFromCourse(Scanner scan) {
		System.out.println("Remove the student from one of their courses");
		System.out.println("Enter the student ID from 1, 2, 3, 4, 5");
		int stIdToRem = scan.nextInt();
		scan.nextLine();
		System.out.println("Enter the course ID from 1, 2, 3, 4, 5");
		int courseIdToRem = scan.nextInt();
		scan.nextLine();
		studentDAO.deleteStudentFromCourse(stIdToRem, courseIdToRem);
	}

	private static void addStudentToCourse(Scanner scan) {
		System.out.println("Add a student to the course (from a list)");
		System.out.println("Enter the student ID from 1, 2, 3, 4, 5");
		int stIdToAdd = scan.nextInt();
		scan.nextLine();
		System.out.println("Enter the course ID from 1, 2, 3, 4, 5");
		int courseIdToAdd = scan.nextInt();
		scan.nextLine();
		studentDAO.addStudentToCourse(stIdToAdd, courseIdToAdd);
		System.out.println();
	}

	private static void deleteStudent(Scanner scan) {
		System.out.println("Delete a student by the STUDENT_ID");
		System.out.println("Enter the student ID");
		int idToDel = scan.nextInt();
		scan.nextLine();
		studentDAO.deleteStudent(idToDel);
		System.out.println();
	}

	private static void addNewStudent(Scanner scan) {
		System.out.println("Add a new student");
		System.out.println("Enter the student ID");
		int id = scan.nextInt();
		scan.nextLine();
		System.out.println("Enter the student first name");
		String firstName = scan.nextLine();
		System.out.println("Enter the student last name");
		String lastName = scan.nextLine();
		studentDAO.addStudent(id, firstName, lastName);
		System.out.println();
	}

	private static void findStudentsRelatedToCourse(Scanner scan) {
		System.out.println("Find all students related to the course with the given name");
		System.out.println(
				"Enter a course name (Mathematics, Science, Health, Handwriting, Art, Music, Leadership, Speech, English, Algebra)");
		String courseName = scan.nextLine();
		studentDAO.findStudentsRelatedToCourse(courseName);
		System.out.println();
	}

	private static void findGroupsWithLessOrEqualStudentsNum(Scanner scan) {
		System.out.println("Find all groups with less or equal students’ number: \n Enter a number between 10 and 30");
		int lessOrEqualNum = scan.nextInt();
		scan.nextLine();
		groupDAO.findGroupsWithStudentsLessOrEqual(lessOrEqualNum);
		System.out.println();
	}
	
	



}
