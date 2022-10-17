package ua.foxminded.javaspring;

import java.util.Scanner;

public class Controller {
	

	static void menu() {
		
		School school = new School();

		Scanner scan = new Scanner(System.in);

		System.out.println("Find all groups with less or equal students’ number: \n Enter a number between 10 and 30");
		int lessOrEqualNum = scan.nextInt();
		scan.nextLine();
		school.findGroupsWithStudentsLessOrEqual(lessOrEqualNum);
		System.out.println();

		System.out.println("Find all students related to the course with the given name");
		System.out.println(
				"Enter a course name (Mathematics, Science, Health, Handwriting, Art, Music, Leadership, Speech, English, Algebra)");
		String courseName = scan.nextLine();
		school.findStudentsRelatedToCourse(courseName);
		System.out.println();

		System.out.println("Add a new student");
		System.out.println("Enter the student ID");
		int id = scan.nextInt();
		scan.nextLine();
		System.out.println("Enter the student first name");
		String firstName = scan.nextLine();
		System.out.println("Enter the student last name");
		String lastName = scan.nextLine();
		school.addStudent(id, firstName, lastName);
		System.out.println();

		System.out.println("Delete a student by the STUDENT_ID");
		System.out.println("Enter the student ID");
		int idToDel = scan.nextInt();
		scan.nextLine();
		school.deleteStudent(idToDel);
		System.out.println();

		System.out.println("Add a student to the course (from a list)");
		System.out.println("Enter the student ID from 1, 2, 3, 4, 5");
		int stIdToAdd = scan.nextInt();
		scan.nextLine();
		System.out.println("Enter the course ID from 1, 2, 3, 4, 5");
		int courseIdToAdd = scan.nextInt();
		scan.nextLine();
		school.addStudentToCourse(stIdToAdd, courseIdToAdd);
		System.out.println();

		System.out.println("Remove the student from one of their courses");
		System.out.println("Enter the student ID from 1, 2, 3, 4, 5");
		int stIdToRem = scan.nextInt();
		scan.nextLine();
		System.out.println("Enter the course ID from 1, 2, 3, 4, 5");
		int courseIdToRem = scan.nextInt();
		scan.nextLine();
		school.deleteStudentFromCourse(stIdToRem, courseIdToRem);

		scan.close();

	}
	
	



}
