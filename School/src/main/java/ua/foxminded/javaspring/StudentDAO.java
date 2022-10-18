package ua.foxminded.javaspring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class StudentDAO {

	String schoolURL = DBConfig.getProperty("url");
	String schoolUsername = DBConfig.getProperty("username");
	String schoolPassword = DBConfig.getProperty("password");

	Random random = new Random();

	void addStudent(int id, String firstName, String lastName) {

		String addStudentQuery = "INSERT INTO school.students (student_id, first_name, last_name) VALUES (?, ?, ?);";
		try (Connection conn = DriverManager.getConnection(schoolURL, schoolUsername, schoolPassword);
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

		try (Connection conn = DriverManager.getConnection(schoolURL, schoolUsername, schoolPassword);
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

	void assignCoursesToAllStudents() {

		StudentDAO studentDAO = new StudentDAO();
		IntStream.rangeClosed(1, 200).forEach(studentDAO::assignCoursesToOneStudent);

	}

	void assignCoursesToOneStudent(int nextStudentID) {

		int numberOfCoursesLimit = (random.nextInt(2) + 1);
		int numberOfAssignedCourses = 1;

		ArrayList<Integer> courseIDs = new ArrayList<>();
		for (int i = 1; i <= CourseDAO.courses.size(); i++)
			courseIDs.add(i);
		Collections.shuffle(courseIDs);
		int randomCourseIDIndex = 0;

		while (numberOfAssignedCourses <= numberOfCoursesLimit) {
			addStudentToCourse(nextStudentID, (courseIDs.get(randomCourseIDIndex)));
			randomCourseIDIndex++;
			numberOfAssignedCourses++;

		}
	}

	void addStudentToCourse(int studentID, int courseID) {

		String addStudentToCourseQuery = "INSERT INTO school.students_courses (student_id, course_id) VALUES (?, ?);";

		try (Connection conn = DriverManager.getConnection(schoolURL, schoolUsername, schoolPassword);
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

		try (Connection conn = DriverManager.getConnection(schoolURL, schoolUsername, schoolPassword);
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

	void findStudentsRelatedToCourse(String courseName) {

		List<String> studentsRelatedToCourse = new ArrayList<>();
		String getStudentsRelatedToCourseQuery = "SELECT school.students.first_name, school.students.last_name "
				+ "FROM school.students_courses "
				+ "INNER JOIN school.students ON school.students.student_id = school.students_courses.student_id "
				+ "WHERE school.students_courses.course_id = "
				+ "(SELECT course_id FROM school.courses WHERE course_name = ?);";

		try (Connection conn = DriverManager.getConnection(schoolURL, schoolUsername, schoolPassword);
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
