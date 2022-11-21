package ua.foxminded.javaspring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CourseDAO {

	private DBConfig dbConfig = new DBConfig();

	void addAllCoursesToDB() {

		Controller.courses.forEach(course -> addCourseToDB(course.getCourseName()));
		System.out.println("Courses added to School database");
	}

	private void addCourseToDB(String courseName) {

		String insertQuery = "INSERT INTO school.courses(course_name) VALUES (?);";

		try (Connection connection = dbConfig.getConnection();
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

			insertStatement.setString(1, courseName);

			insertStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
		}
		System.out.println("Course " + courseName + " added to database");
	}

	void addStudentsCoursesAssignmentsToDB() {
		IntStream.rangeClosed(1, Controller.students.size()).forEach(this::addOneStudentCoursesAssignmentsToDB);

		System.out.println("Students' assignments to courses added to School database");
	}

	void addOneStudentCoursesAssignmentsToDB(int studentID) {
		List<Course> coursesOfStudent = Controller.students.get(studentID - 1).getCourses();
		for (Course course : coursesOfStudent) {
			addStudentCourseAssignmentInDB(studentID, course.getCourseID());
		}
	}

	void addStudentCourseAssignmentInDB(int studentID, int courseID) {

		String insertQuery = "INSERT INTO school.students_courses (student_id, course_id) VALUES (?, ?);";

		try (Connection connection = dbConfig.getConnection();
				PreparedStatement insertStatment = connection.prepareStatement(insertQuery)) {

			insertStatment.setInt(1, studentID);
			insertStatment.setInt(2, courseID);

			insertStatment.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
		}

		System.out.println("Student with ID " + studentID + " assigned to course " + courseID);
	}

	List<Integer> getCourseIDsOfStudent(int studentID) {

		List<Integer> studentCourses = new ArrayList<>();
		String selectQuery = "SELECT course_id FROM school.students_courses WHERE student_id = ?;";

		try (Connection connection = dbConfig.getConnection();
				PreparedStatement selectStatement = connection.prepareStatement(selectQuery);) {

			selectStatement.setInt(1, studentID);
			ResultSet rs = selectStatement.executeQuery();
			while (rs.next()) {
				studentCourses.add(rs.getInt("course_id"));
			}
		} catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
		}
		return studentCourses;
	}

	void deleteStudentFromCourse(int studentID, int courseID) {

		String deleteQuery = "DELETE FROM school.students_courses WHERE student_id = ? AND course_id = ?;";

		try (Connection connection = dbConfig.getConnection();
				PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {

			deleteStatement.setInt(1, studentID);
			deleteStatement.setInt(2, courseID);

			deleteStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
		}

		System.out.println("Student with ID " + studentID + " removed from course with ID " + courseID);
	}

	List<Student> getStudentsRelatedToCourse(String courseName) {

		List<Student> studentsRelatedToCourse = new ArrayList<>();
		String selectQuery = "SELECT school.students.student_id, school.students.first_name, school.students.last_name FROM school.students "
				+ "INNER JOIN school.students_courses ON school.students.student_id = school.students_courses.student_id "
				+ "INNER JOIN school.courses ON school.courses.course_id = school.students_courses.course_id "
				+ "WHERE course_name = ?;";

		try (Connection connection = dbConfig.getConnection();
				PreparedStatement selectStatement = connection.prepareStatement(selectQuery);) {

			selectStatement.setString(1, courseName);
			ResultSet rs = selectStatement.executeQuery();

			while (rs.next()) {
				studentsRelatedToCourse.add(new Student((rs.getInt("student_id")), (rs.getString("first_name")),
						(rs.getString("last_name"))));
			}

		}

		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
		}

		return studentsRelatedToCourse;

	}

}
