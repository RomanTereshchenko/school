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

public class CourseDAO {

	private DBConfig dbConfig;
	private CourseGenerator courseGenerator;
	private Random random = new Random();

	void addAllCoursesToDB() {
		
		IntStream.rangeClosed(1, courseGenerator.courses.size())
				.forEach(courseNumber -> addCourseToDB(courseGenerator.courses.get(courseNumber - 1).getCourseName()));
	}

	private void addCourseToDB(String name) {

		String addCourseQuery = "INSERT INTO school.courses(course_name) VALUES (?);";

		try (Connection connection = DriverManager.getConnection(dbConfig.schoolURL, dbConfig.schoolUsername,
				dbConfig.schoolPassword);
				PreparedStatement addCourseStatment = connection.prepareStatement(addCourseQuery)) {

			addCourseStatment.setString(1, name);

			addCourseStatment.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}
	}

	void assignCoursesToAllStudents() {

		IntStream.rangeClosed(1, 200).forEach(this::assignCoursesToOneStudent);

	}

	private void assignCoursesToOneStudent(int nextStudentID) {

		int numberOfCoursesLimit = (random.nextInt(2) + 1);
		int numberOfAssignedCourses = 1;

		ArrayList<Integer> courseIDs = new ArrayList<>();
		for (int i = 1; i <= courseGenerator.courses.size(); i++)
			courseIDs.add(i);
		Collections.shuffle(courseIDs);
		int randomCourseIDIndex = 0;

		while (numberOfAssignedCourses <= numberOfCoursesLimit) {
			addStudentToCourse(nextStudentID, (courseIDs.get(randomCourseIDIndex)));
			randomCourseIDIndex++;
			numberOfAssignedCourses++;

		}
	}

	List<Integer> getCoursesOfStudent(int studentID) {

		List<Integer> studentCourses = new ArrayList<>();
		String getQuery = "SELECT course_id FROM school.students_courses WHERE student_id = ?;";

		try (Connection connection = DriverManager.getConnection(dbConfig.schoolURL, dbConfig.schoolUsername,
				dbConfig.schoolPassword);
				PreparedStatement getStudentsCoursesStatement = connection.prepareStatement(getQuery);) {

			getStudentsCoursesStatement.setInt(1, studentID);
			ResultSet rs = getStudentsCoursesStatement.executeQuery();
			while (rs.next()) {
				studentCourses.add(rs.getInt("course_id"));
			}
		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}
		return studentCourses;
	}

	void addStudentToCourse(int studentID, int courseID) {

		String addStudentToCourseQuery = "INSERT INTO school.students_courses (student_id, course_id) VALUES (?, ?);";

		try (Connection connection = DriverManager.getConnection(dbConfig.schoolURL, dbConfig.schoolUsername,
				dbConfig.schoolPassword);
				PreparedStatement addStudentToCourseStatment = connection.prepareStatement(addStudentToCourseQuery)) {

			addStudentToCourseStatment.setInt(1, studentID);
			addStudentToCourseStatment.setInt(2, courseID);

			addStudentToCourseStatment.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}

		System.out.println("Student with ID " + studentID + " added to course " + courseID);
	}

	void deleteStudentFromCourse(int studentID, int courseID) {

		String deleteStudentFromCourseQuery = "DELETE FROM school.students_courses WHERE student_id = ? AND course_id = ?;";

		try (Connection connection = DriverManager.getConnection(dbConfig.schoolURL, dbConfig.schoolUsername,
				dbConfig.schoolPassword);
				PreparedStatement deleteStudentFromCourseStatment = connection
						.prepareStatement(deleteStudentFromCourseQuery)) {

			deleteStudentFromCourseStatment.setInt(1, studentID);
			deleteStudentFromCourseStatment.setInt(2, courseID);

			deleteStudentFromCourseStatment.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}

		System.out.println("Student with ID " + studentID + " removed from course with ID " + courseID);
	}

	List<String> getStudentsRelatedToCourse(String courseName) {

		List<String> studentsRelatedToCourse = new ArrayList<>();
		String getStudentsRelatedToCourseQuery = "SELECT school.students.first_name, school.students.last_name FROM school.students "
				+ "INNER JOIN school.students_courses ON school.students.student_id = school.students_courses.student_id "
				+ "INNER JOIN school.courses ON school.courses.course_id = school.students_courses.course_id "
				+ "WHERE course_name = ?;";

		try (Connection connection = DriverManager.getConnection(dbConfig.schoolURL, dbConfig.schoolUsername,
				dbConfig.schoolPassword);
				PreparedStatement getStudentsRelatedToCourseStatment = connection
						.prepareStatement(getStudentsRelatedToCourseQuery);) {

			getStudentsRelatedToCourseStatment.setString(1, courseName);
			ResultSet rs = getStudentsRelatedToCourseStatment.executeQuery();

			while (rs.next()) {
				studentsRelatedToCourse.add(rs.getString("first_name") + " " + rs.getString("last_name"));
			}

		}

		catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}

		return studentsRelatedToCourse;

	}

}
