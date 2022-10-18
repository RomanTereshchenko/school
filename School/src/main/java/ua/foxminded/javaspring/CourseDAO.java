package ua.foxminded.javaspring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class CourseDAO {
	
	static List<String> courses = Arrays.asList("Mathematics", "Science", "Health", "Handwriting", "Art", "Music",
			"Leadership", "Speech", "English", "Algebra");
	
	String schoolURL = DBConfig.getProperty("url");
	String schoolUsername = DBConfig.getProperty("username");
	String schoolPassword = DBConfig.getProperty("password");
	
	void createCourses() {
		IntStream.rangeClosed(1, courses.size())
				.forEach(courseNumber -> addCourse(courseNumber, courses.get(courseNumber - 1)));
		System.out.println("Courses created");
	}
	
	void addCourse(int id, String name) {

		String addCourseQuery = "INSERT INTO school.courses VALUES (?, ?);";

		try (Connection conn = DriverManager.getConnection(schoolURL, schoolUsername, schoolPassword);
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
	
	

}
