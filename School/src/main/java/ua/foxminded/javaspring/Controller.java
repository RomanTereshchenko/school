package ua.foxminded.javaspring;

import java.util.List;
import java.util.Scanner;
import static java.lang.System.exit;

public class Controller {

	private TablesDAO tablesDao = new TablesDAO();
	private GroupGenerator groupGenerator = new GroupGenerator();
	private GroupDAO groupDao = new GroupDAO();
	private CourseDAO courseDao = new CourseDAO();
	private StudentGenerator studentGenerator = new StudentGenerator();
	private StudentDAO studentDao = new StudentDAO();
	private CourseGenerator courseGenerator = new CourseGenerator();
	private Scanner scan = new Scanner(System.in);

	void startUp() {

		tablesDao.createSchemaAndTables();

		groupGenerator.generateGroups();
		groupDao.addGroupsToDB();

		courseGenerator.generateCourses();
		courseDao.addAllCoursesToDB();

		studentGenerator.generateStudents();
		System.out.println(studentGenerator.students);
		studentDao.printStudents();
//		studentDao.addStudentsToDB();
//
//		studentGenerator.assignAllGroupsToAllItsStudents();
//		groupDao.addGroupIDToAllTheirStudentsInDB();
//
//		studentGenerator.assignCoursesToAllStudents();
//		courseDao.addStudentsCoursesAssignmentsInDB();
	}

	void menu() {

		String[] options = { "1 - Find all groups with less or equal students’ number",
				"2 - Find all students related to the course with the given name", "3 - Add a new student",
				"4 - Delete a student by the STUDENT_ID", "5 - Add a student to the course (from a list)",
				"6 - Remove the student from one of their courses", "7 - Exit", };

		int option = 1;
		while (option != 7) {
			printMenu(options);
			try {
				option = scan.nextInt();
				switch (option) {
				case 1:
					findGroupsWithLessOrEqualStudentsNum();
					break;
				case 2:
					findStudentsRelatedToCourse();
					break;
				case 3:
					addNewStudent();
					break;
				case 4:
					delStudent();
					break;
				case 5:
					addStudentToCourse();
					break;
				case 6:
					removeStudentFromCourse();
					break;
				case 7:
					exit(0);
				default:
					exit(0);
				}
			} catch (Exception e) {
				System.out.println("Please, enter an integer value between 1 and " + options.length);
				scan.next();
			}
		}
		scan.close();

	}

	private void printMenu(String[] options) {
		for (String option : options) {
			System.out.println(option);
		}
		System.out.println("Choose your option : ");
	}

	private void removeStudentFromCourse() {
		System.out.println("Remove the student from one of their courses");
		System.out.println("Enter the student ID");
		int stIdToRem = scan.nextInt();
		scan.nextLine();
		System.out.println("Enter the course ID");
		int courseIdToRem = scan.nextInt();
		scan.nextLine();
		courseDao.deleteStudentFromCourse(stIdToRem, courseIdToRem);
	}

	private void addStudentToCourse() {
		System.out.println("Add a student to the course (from a list)");
		System.out.println("Enter the student ID");
		int stIdToAdd = scan.nextInt();
		scan.nextLine();
		System.out.println("Enter the course ID");
		int courseIdToAdd = scan.nextInt();
		scan.nextLine();
		List<Integer> studentCourses = courseDao.getCoursesOfStudent(stIdToAdd);
		if (studentCourses.contains(courseIdToAdd)) {
			System.out.println("This student is already addigned to this course. Choose other student and course.");
		} else
			courseDao.addStudentCourseAssignmentInDB(stIdToAdd, courseIdToAdd);
		System.out.println();
	}

	private void delStudent() {
		System.out.println("Delete a student by the STUDENT_ID");
		System.out.println("Enter the student ID");
		int idToDel = scan.nextInt();
		scan.nextLine();
		studentDao.deleteStudent(idToDel);
		System.out.println();
	}

	private void addNewStudent() {
		System.out.println("Add a new student");
		System.out.println("Enter the student first name");
		scan.nextLine();
		String firstName = scan.nextLine();
		System.out.println("Enter the student last name");
		String lastName = scan.nextLine();
		studentDao.addStudent(firstName, lastName);
		System.out.println();
	}

	private void findStudentsRelatedToCourse() {
		System.out.println("Find all students related to the course with the given name");
		System.out.println(
				"Enter a course name (Mathematics, Science, Health, Handwriting, Art, Music, Leadership, Speech, English, Algebra)");
		scan.nextLine();
		String courseName = scan.nextLine();
		System.out.println(courseDao.getStudentsRelatedToCourse(courseName));
		System.out.println();
	}

	private void findGroupsWithLessOrEqualStudentsNum() {
		System.out.println("Find all groups with less or equal students’ number: \n Enter a number between 10 and 30");
		int lessOrEqualNum = scan.nextInt();
		scan.nextLine();
		System.out.println(groupDao.findGroupsWithStudentsLessOrEqual(lessOrEqualNum));
		System.out.println();
	}

}
