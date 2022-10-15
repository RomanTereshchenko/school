package ua.foxminded.javaspring;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class School {

	String pathToSchemaAndTablesCreatingFile = "createScholSchema&Tables.sql";
	public static final String SCHOOL_DB_URL = "jdbc:postgresql://localhost:5432/school";
	public static final String SCHOOL_DB_USERNAME = "postgres";
	public static final String SCHOOL_DB_PASSWORD = "1234";
	Random random = new Random();

	List<String> groups = IntStream.rangeClosed(1, 10)
			.mapToObj(number -> (new StringBuilder().append(((char) (random.nextInt(26) + 'a'))).append(
					((char) (new Random().nextInt(26) + 'a'))) + ("-" + random.nextInt(10) + random.nextInt(10)))
					.toString())
			.collect(Collectors.toList());

	List<String> courses = Arrays.asList("Mathematics", "Science", "Health", "Handwriting", "Art", "Music",
			"Leadership", "Speech", "English", "Algebra");

	List<String> studentFirstNames = Arrays.asList("Lexi", "Elouise", "Wilbur", "Glenda", "Judah", "Salahuddin",
			"Juliet", "Tanner", "Luella", "Enid", "Hadiya", "Rares", "Bryan", "Patsy", "Eshan", "Lester", "Bentley",
			"Yu", "Finlay", "Sylvie");

	List<String> studentLastNames = Arrays.asList("Ferry", "Buck", "Moody", "Craft", "Ridley", "Aguilar", "Garrett",
			"Peralta", "Mcknight", "O'Quinn", "Simons", "Kelley", "Trejo", "Dougherty", "Palacios", "Murphy", "Gordon",
			"Mcgee", "Strong", "Philip");

	int nextUnassignedStudentID = 1;
	static School school = new School();

	public static void main(String[] args) {

		school.menu();

	}

	void menu() {

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

	void createSchemaAndTables() {

		try (Connection conn = DriverManager.getConnection(SCHOOL_DB_URL, SCHOOL_DB_USERNAME, SCHOOL_DB_PASSWORD);
				PreparedStatement st = conn
						.prepareStatement(readCreatingTablesScript(pathToSchemaAndTablesCreatingFile))) {
			System.out.println("Connected to database");
			st.executeUpdate();
			System.out.println("Tables created");
		} catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
		}

	}

	String readCreatingTablesScript(String scriptFilePath) {

		List<String> scriptLines = new ArrayList<>();

		try (Stream<String> stream = Files.lines(Paths.get(scriptFilePath))) {

			scriptLines = stream.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return String.join("\n", scriptLines);

	}

	void createGroups() {
		IntStream.rangeClosed(1, school.groups.size())
				.forEach(number -> school.addGroup(number, groups.get(number - 1)));
		System.out.println("Groups created");
	}

	void addGroup(int id, String name) {

		String addGroupQuery = "INSERT INTO school.groups VALUES(?, ?);";

		try (Connection conn = DriverManager.getConnection(SCHOOL_DB_URL, SCHOOL_DB_USERNAME, SCHOOL_DB_PASSWORD);
				PreparedStatement addGroupStatm = conn.prepareStatement(addGroupQuery);) {
			System.out.println("Connected to database");

			addGroupStatm.setInt(1, id);
			addGroupStatm.setString(2, name);

			addGroupStatm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}

	}

	List<String> getGroups() {

		List<String> groupsFromSchool = new ArrayList<>();

		String getGroupsQuery = "SELECT * from school.groups;";

		try (Connection conn = DriverManager.getConnection(SCHOOL_DB_URL, SCHOOL_DB_USERNAME, SCHOOL_DB_PASSWORD);
				PreparedStatement getBookStatm = conn.prepareStatement(getGroupsQuery);
				ResultSet rs = getBookStatm.executeQuery();) {
			System.out.println("Connected to database");

			while (rs.next()) {
				groupsFromSchool.add(rs.getString("group_name"));
			}
		}

		catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}

		return groupsFromSchool;

	}

	void createCourses() {
		IntStream.rangeClosed(1, school.courses.size())
				.forEach(courseNumber -> school.addCourse(courseNumber, courses.get(courseNumber - 1)));
		System.out.println("Courses created");
	}

	void addCourse(int id, String name) {

		String addCourseQuery = "INSERT INTO school.courses VALUES (?, ?);";

		try (Connection conn = DriverManager.getConnection(SCHOOL_DB_URL, SCHOOL_DB_USERNAME, SCHOOL_DB_PASSWORD);
				PreparedStatement addCourseStatm = conn.prepareStatement(addCourseQuery)) {
			System.out.println("Connected to database");

			addCourseStatm.setInt(1, id);
			addCourseStatm.setString(2, name);

			addCourseStatm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}
	}

	void createStudents() {

		IntStream.rangeClosed(1, 200).forEach(studentNumber -> school.addStudent(studentNumber,
				studentFirstNames.get(random.nextInt(20)), studentLastNames.get(random.nextInt(20))));
		System.out.println("Students created");
	}

	void addStudent(int id, String firstName, String lastName) {

		String addStudentQuery = "INSERT INTO school.students (student_id, first_name, last_name) VALUES (?, ?, ?);";
		try (Connection conn = DriverManager.getConnection(SCHOOL_DB_URL, SCHOOL_DB_USERNAME, SCHOOL_DB_PASSWORD);
				PreparedStatement addStudentStatm = conn.prepareStatement(addStudentQuery)) {
			System.out.println("Connected to database");

			addStudentStatm.setInt(1, id);
			addStudentStatm.setString(2, firstName);
			addStudentStatm.setString(3, lastName);

			addStudentStatm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}

		System.out.println("New student " + firstName + " " + lastName + " is added to the database with ID " + id);
	}

	void deleteStudent(int id) {

		String deleteStudentQuery = "DELETE FROM school.students WHERE student_id = ?;";

		try (Connection conn = DriverManager.getConnection(SCHOOL_DB_URL, SCHOOL_DB_USERNAME, SCHOOL_DB_PASSWORD);
				PreparedStatement deleteStudentStatm = conn.prepareStatement(deleteStudentQuery)) {
			System.out.println("Connected to database");

			deleteStudentStatm.setInt(1, id);

			deleteStudentStatm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}

		System.out.println("Student with the id " + id + " is deleted from the database");
	}

	void buildAllGroups() {
		IntStream.rangeClosed(1, groups.size()).forEach(school::buildOneGroup);
	}

	void buildOneGroup(int groupID) {

		int limitOfStudentsInGroup = ((random.nextInt(20)) + 10);
		int numberOfStudentsInGroup = 0;
		School school = new School();

		while (numberOfStudentsInGroup < limitOfStudentsInGroup) {
			school.assignStudentWithGroup(groupID, nextUnassignedStudentID);
			numberOfStudentsInGroup++;
			nextUnassignedStudentID++;
		}

	}

	void assignStudentWithGroup(int groupID, int unassignedStudentID) {

		String assignGroupToStudentQuery = "UPDATE school.students SET group_id = ? WHERE student_id = ? AND group_id IS NULL;";

		try (Connection conn = DriverManager.getConnection(SCHOOL_DB_URL, SCHOOL_DB_USERNAME, SCHOOL_DB_PASSWORD);
				PreparedStatement assignGroupToStudentStatm = conn.prepareStatement(assignGroupToStudentQuery)) {
			System.out.println("Connected to database");

			assignGroupToStudentStatm.setInt(1, groupID);
			assignGroupToStudentStatm.setInt(2, unassignedStudentID);

			assignGroupToStudentStatm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}
	}

	void assignCoursesToAllStudents() {

		IntStream.rangeClosed(1, 200).forEach(school::assignCoursesToOneStudent);

	}

	void assignCoursesToOneStudent(int nextStudentID) {

		int numberOfCoursesLimit = (random.nextInt(2) + 1);
		int numberOfAssignedCourses = 1;

		ArrayList<Integer> courseIDs = new ArrayList<>();
		for (int i = 1; i <= courses.size(); i++)
			courseIDs.add(i);
		Collections.shuffle(courseIDs);
		int randomCourseIDIndex = 0;

		while (numberOfAssignedCourses <= numberOfCoursesLimit) {
			school.addStudentToCourse(nextStudentID, (courseIDs.get(randomCourseIDIndex)));
			randomCourseIDIndex++;
			numberOfAssignedCourses++;

		}
	}

	void addStudentToCourse(int studentID, int courseID) {

		String addStudentToCourseQuery = "INSERT INTO school.students_courses (student_id, course_id) VALUES (?, ?);";

		try (Connection conn = DriverManager.getConnection(SCHOOL_DB_URL, SCHOOL_DB_USERNAME, SCHOOL_DB_PASSWORD);
				PreparedStatement addStudentToCourseStatm = conn.prepareStatement(addStudentToCourseQuery)) {
			System.out.println("Connected to database");

			addStudentToCourseStatm.setInt(1, studentID);
			addStudentToCourseStatm.setInt(2, courseID);

			addStudentToCourseStatm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}

		System.out.println("Student with ID " + studentID + " added to course " + courseID);
	}

	void deleteStudentFromCourse(int studentID, int courseID) {

		String deleteStudentFromCourseQuery = "DELETE FROM school.students_courses WHERE student_id = ? AND course_id = ?;";

		try (Connection conn = DriverManager.getConnection(SCHOOL_DB_URL, SCHOOL_DB_USERNAME, SCHOOL_DB_PASSWORD);
				PreparedStatement deleteStudentFromCourseStatm = conn.prepareStatement(deleteStudentFromCourseQuery)) {
			System.out.println("Connected to database");

			deleteStudentFromCourseStatm.setInt(1, studentID);
			deleteStudentFromCourseStatm.setInt(2, courseID);

			deleteStudentFromCourseStatm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}

		System.out.println("Student with ID " + studentID + " removed from course " + courseID);
	}

	void findGroupsWithStudentsLessOrEqual(int studentsInGroup) {
		createGroupsWithStudentsLessOrEqual(studentsInGroup);
		System.out.println(getGroupsWithStudentsLessOrEqual());
	}

	void createGroupsWithStudentsLessOrEqual(int studentsInGroup) {

		String groupsWithStudentsLessOrEqualQuery = "DROP TABLE IF EXISTS school.groups_students_count; "
				+ "CREATE TABLE school.groups_students_count (group_id, student_count)" + "AS "
				+ "SELECT group_id, COUNT (group_id) FROM school.students GROUP BY group_id; "
				+ "DELETE from school.groups_students_count WHERE student_count > ?;";

		try (Connection conn = DriverManager.getConnection(SCHOOL_DB_URL, SCHOOL_DB_USERNAME, SCHOOL_DB_PASSWORD);
				PreparedStatement groupsWithStudentsLessOrEqualStatm = conn
						.prepareStatement(groupsWithStudentsLessOrEqualQuery)) {
			System.out.println("Connected to database");

			groupsWithStudentsLessOrEqualStatm.setInt(1, studentsInGroup);
			groupsWithStudentsLessOrEqualStatm.executeUpdate();
			System.out.println("Groups with students less or equal " + studentsInGroup + " found");
		} catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
		}

	}

	List<String> getGroupsWithStudentsLessOrEqual() {

		List<String> groupsWithStudentsLessOrEqual = new ArrayList<>();
		String getGroupsWithStudentsLessOrEqualQuery = "SELECT school.groups.group_id, group_name FROM school.groups "
				+ "INNER JOIN school.groups_students_count ON school.groups.group_id = school.groups_students_count.group_id;";

		try (Connection conn = DriverManager.getConnection(SCHOOL_DB_URL, SCHOOL_DB_USERNAME, SCHOOL_DB_PASSWORD);
				PreparedStatement getgroupsWithStudentsLessOrEqualStatm = conn
						.prepareStatement(getGroupsWithStudentsLessOrEqualQuery);
				ResultSet rs = getgroupsWithStudentsLessOrEqualStatm.executeQuery();) {
			System.out.println("Connected to database");

			while (rs.next()) {
				groupsWithStudentsLessOrEqual.add(rs.getString("group_name"));
			}
		}

		catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}

		return groupsWithStudentsLessOrEqual;

	}

	void findStudentsRelatedToCourse(String courseName) {

		List<String> studentsRelatedToCourse = new ArrayList<>();
		String getStudentsRelatedToCourseQuery = "SELECT school.students.first_name, school.students.last_name "
				+ "FROM school.students_courses "
				+ "INNER JOIN school.students ON school.students.student_id = school.students_courses.student_id "
				+ "WHERE school.students_courses.course_id = "
				+ "(SELECT course_id FROM school.courses WHERE course_name = ?);";

		try (Connection conn = DriverManager.getConnection(SCHOOL_DB_URL, SCHOOL_DB_USERNAME, SCHOOL_DB_PASSWORD);
				PreparedStatement getStudentsRelatedToCourseStatm = conn
						.prepareStatement(getStudentsRelatedToCourseQuery);) {
			System.out.println("Connected to database");

			getStudentsRelatedToCourseStatm.setString(1, courseName);
			ResultSet rs = getStudentsRelatedToCourseStatm.executeQuery();

			while (rs.next()) {
				studentsRelatedToCourse.add(rs.getString("first_name") + " " + rs.getString("last_name"));
			}
		}

		catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}

		System.out.println(studentsRelatedToCourse);

	}

}
