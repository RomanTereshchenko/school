package ua.foxminded.javaspring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import static java.lang.System.exit;

public class Controller {

	private TablesDAO tablesDao = new TablesDAO();
	GroupGenerator groupGenerator = new GroupGenerator();
	private GroupDAO groupDao = new GroupDAO();
	private CourseDAO courseDao = new CourseDAO();
	private StudentGenerator studentGenerator = new StudentGenerator();
	private StudentDAO studentDao = new StudentDAO();
	private CourseGenerator courseGenerator = new CourseGenerator();
	private Scanner scan = new Scanner(System.in);
	private int groupsNumber = 10;
	private int studentsNumber = 200;
	List<String> studentFirstNames = Arrays.asList("Lexi", "Elouise", "Wilbur", "Glenda", "Judah", "Salahuddin",
	"Juliet", "Tanner", "Luella", "Enid", "Hadiya", "Rares", "Bryan", "Patsy", "Eshan", "Lester", "Bentley",
	"Yu", "Finlay", "Sylvie");
	List<String> studentLastNames = Arrays.asList("Ferry", "Buck", "Moody", "Craft", "Ridley", "Aguilar",
	"Garrett", "Peralta", "Mcknight", "O'Quinn", "Simons", "Kelley", "Trejo", "Dougherty", "Palacios", "Murphy",
	"Gordon", "Mcgee", "Strong", "Philip");
	final List<String> courseNames = Arrays.asList("Mathematics", "Science", "Health", "Handwriting", "Art",
	"Music", "Leadership", "Speech", "English", "Algebra");
	static List<Student> students = new ArrayList<>();
	static List<Course> courses = new ArrayList<>();
	static List<Group> groups = new ArrayList<>();


	void startUp() {

		tablesDao.createSchemaAndTables();
		
		groups = groupGenerator.generateNGroups(groupsNumber);
		groupDao.addGroupsToDB();

		courses = courseGenerator.generateCourses();
		courseDao.addAllCoursesToDB();

		students = studentGenerator.generateNStudents(studentsNumber);
		studentDao.addStudentsToDB();

		studentGenerator.assignAllGroupsToAllItsStudents();
		groupDao.addGroupIDToAllTheirStudentsInDB();

		studentGenerator.assignCoursesToAllStudents();
		courseDao.addStudentsCoursesAssignmentsToDB();
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
					findGroupsByStudentsCount();
					break;
				case 2:
					findStudentsRelatedToCourse();
					break;
				case 3:
					addNewStudent();
					break;
				case 4:
					deleteStudent();
					break;
				case 5:
					addStudentToCourse();
					break;
				case 6:
					removeStudentFromCourse();
					break;
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
		int studentIdToRemove = scan.nextInt();
		scan.nextLine();
		System.out.println("Enter the course ID");
		int courseIdToRemove = scan.nextInt();
		scan.nextLine();
		courseDao.deleteStudentFromCourse(studentIdToRemove, courseIdToRemove);
	}

	private void addStudentToCourse() {
		System.out.println("Add a student to the course (from a list)");
		System.out.println("Enter the student ID");
		int studentId = scan.nextInt();
		scan.nextLine();
		System.out.println("Enter the course ID");
		int courseId = scan.nextInt();
		scan.nextLine();
		List<Integer> studentCourses = courseDao.getCourseIDsOfStudent(studentId);
		if (studentCourses.contains(courseId)) {
			System.out.println("This student is already addigned to this course. Choose other student and course.");
		} else
			courseDao.addStudentCourseAssignmentInDB(studentId, courseId);
		System.out.println();
	}

	private void deleteStudent() {
		System.out.println("Delete a student by the STUDENT_ID");
		System.out.println("Enter the student ID");
		int studentIdToDelete = scan.nextInt();
		scan.nextLine();
		studentDao.deleteStudentFromDB(studentIdToDelete);
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
		List<Student> studentsOfCourse = courseDao.getStudentsRelatedToCourse(courseName);
		for (Student student : studentsOfCourse) {
			System.out.println(student.getFirstName() + " " + student.getLastName());
		}
		System.out.println();
	}

	private void findGroupsByStudentsCount() {
		System.out.println("Find all groups with less or equal students’ number: \n Enter a number between 10 and 30");
		int lessOrEqualNum = scan.nextInt();
		scan.nextLine();
		System.out.println(groupDao.selectGroupsByStudentsCount(lessOrEqualNum));
		System.out.println();
	}

}
