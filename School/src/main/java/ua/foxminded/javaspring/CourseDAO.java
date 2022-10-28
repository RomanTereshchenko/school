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
	private CourseGenerator courseGenerator = new CourseGenerator();
	private StudentGenerator studentGenerator = new StudentGenerator();

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

	void addStudentsCoursesAssignmentsInDB() {
		IntStream.rangeClosed(0, (studentGenerator.students.size() - 1)).forEach(this::addOneStudentCoursesAssignmentsInDB);
	}
	
	void addOneStudentCoursesAssignmentsInDB(int studentID) {
		List<Course> coursesOfStudent = studentGenerator.students.get(studentID).getCourses();
		System.out.println(coursesOfStudent);
		for (Course course : coursesOfStudent) {
			addStudentCourseAssignmentInDB(studentID, course.getCourseID());
		}
	}

	void addStudentCourseAssignmentInDB(int studentID, int courseID) {

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
