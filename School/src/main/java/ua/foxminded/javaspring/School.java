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
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class School {

	String pathToSchemaAndTablesCreatingFile = "createScholSchema&Tables.sql";
	public static final String SCHOOL_DB_URL = "jdbc:postgresql://localhost:5432/school";
	public static final String SCHOOL_DB_USERNAME = "postgres";
	public static final String SCHOOL_DB_PASSWORD = "1234";

	List<String> groups = IntStream.rangeClosed(1, 10)
			.mapToObj(number -> (new StringBuilder().append(((char) (new Random().nextInt(26) + 'a')))
					.append(((char) (new Random().nextInt(26) + 'a')))
					+ ("-" + new Random().nextInt(10) + new Random().nextInt(10))).toString())
			.collect(Collectors.toList());

	List<String> courses = Arrays.asList("Mathematics", "Science", "Health", "Handwriting", "Art", "Music",
			"Leadership", "Speech", "English", "Algebra");

	List<String> studentFirstNames = Arrays.asList("Lexi", "Elouise", "Wilbur", "Glenda", "Judah", "Salahuddin",
			"Juliet", "Tanner", "Luella", "Enid", "Hadiya", "Rares", "Bryan", "Patsy", "Eshan", "Lester", "Bentley",
			"Yu", "Finlay", "Sylvie");

	List<String> studentLastNames = Arrays.asList("Ferry", "Buck", "Moody", "Craft", "Ridley", "Aguilar", "Garrett",
			"Peralta", "Mcknight", "O'Quinn", "Simons", "Kelley", "Trejo", "Dougherty", "Palacios", "Murphy", "Gordon",
			"Mcgee", "Strong", "Philip");
	
	Random random = new Random();

	public static void main(String[] args) {

		School school = new School();

		school.buildAllGroups();

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
		School school = new School();
		IntStream.rangeClosed(1, school.groups.size())
				.forEach(number -> school.addGroup(number, groups.get(number - 1)));
		System.out.println("Groups created");
	}

	void addGroup(int id, String name) {

		String addGroupString = "INSERT INTO school.groups VALUES(?, ?);";
		try (Connection conn = DriverManager.getConnection(SCHOOL_DB_URL, SCHOOL_DB_USERNAME, SCHOOL_DB_PASSWORD);
				PreparedStatement addGroupStatm = conn.prepareStatement(addGroupString);) {
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

		String getGroupsString = "SELECT * from school.groups;";

		try (Connection conn = DriverManager.getConnection(SCHOOL_DB_URL, SCHOOL_DB_USERNAME, SCHOOL_DB_PASSWORD);
				PreparedStatement getBookStatm = conn.prepareStatement(getGroupsString);
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
		School school = new School();
		IntStream.rangeClosed(1, school.courses.size())
				.forEach(courseNumber -> school.addCourse(courseNumber, courses.get(courseNumber - 1)));
		System.out.println("Courses created");
	}

	void addCourse(int id, String name) {

		String addCourseString = "INSERT INTO school.courses VALUES (?, ?);";
		try (Connection conn = DriverManager.getConnection(SCHOOL_DB_URL, SCHOOL_DB_USERNAME, SCHOOL_DB_PASSWORD);
				PreparedStatement addCourseStatm = conn.prepareStatement(addCourseString)) {
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
		School school = new School();
		IntStream.rangeClosed(1, 200)
				.forEach(studentNumber -> school.addStudent(studentNumber,
						studentFirstNames.get(random.nextInt(20)),
						studentLastNames.get(random.nextInt(20))));
		System.out.println("Students created");
	}

	void addStudent(int id, String firstName, String lastName) {

		String addStudentString = "INSERT INTO school.students (student_id, first_name, last_name) VALUES (?, ?, ?);";
		try (Connection conn = DriverManager.getConnection(SCHOOL_DB_URL, SCHOOL_DB_USERNAME, SCHOOL_DB_PASSWORD);
				PreparedStatement addStudentStatm = conn.prepareStatement(addStudentString)) {
			System.out.println("Connected to database");

			addStudentStatm.setInt(1, id);
			addStudentStatm.setString(2, firstName);
			addStudentStatm.setString(3, lastName);

			addStudentStatm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}
	}
	
//	void buildAllGroups () {
//		School school = new School();
//		IntStream.rangeClosed(1, groups.size()).forEach(groupID -> school.buildOneGroup(groupID));
//	}
	
	void buildAllGroups() {
		School school = new School();
		for (int i = 1; i < 21; i++) {
			school.buildOneGroup(i);
		}
	}
	
	void buildOneGroup(int groupID) {
		
		int limitOfStudentsInGroup = ((random.nextInt(20)) + 10);
		int numberOfStudentsInGroup = 0;
		
		School school = new School();
		
		while (numberOfStudentsInGroup < limitOfStudentsInGroup) {
		school.assignStudentWithGroup(groupID);	
		numberOfStudentsInGroup++;
		System.out.println(numberOfStudentsInGroup);
		System.out.println(groupID);
		}
		
	}
	
	void assignStudentWithGroup(int groupID) {
		
		String assignGroupToStudentQuery = "UPDATE school.students SET group_id = ? WHERE group_id IS NULL;";
		
		try (Connection conn = DriverManager.getConnection(SCHOOL_DB_URL, SCHOOL_DB_USERNAME, SCHOOL_DB_PASSWORD);
				PreparedStatement assignGroupToStudentStatm = conn.prepareStatement(assignGroupToStudentQuery)) {
			System.out.println("Connected to database");

			assignGroupToStudentStatm.setInt(1, groupID);

			assignGroupToStudentStatm.executeUpdate();
			System.out.println(groupID);

		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}

	}
}
