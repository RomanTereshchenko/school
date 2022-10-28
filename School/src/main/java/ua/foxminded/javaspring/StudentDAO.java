package ua.foxminded.javaspring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.IntStream;

public class StudentDAO {

	private DBConfig dbConfig = new DBConfig();
	private StudentGenerator studentGenerator = new StudentGenerator();


	void printStudents() {
		System.out.println(studentGenerator.studentLastNames);
		System.out.println(studentGenerator.students);
	}
	
	void addStudentsToDB() {
		
		IntStream.rangeClosed(0, 199)
				.forEach(studentID -> addStudent((studentGenerator.students.get(studentID).getFirstName()),
						(studentGenerator.students.get(studentID).getLastName())));
		System.out.println("Students added to School database");
	}

	void addStudent(String firstName, String lastName) {

		String addStudentQuery = "INSERT INTO school.students (first_name, last_name) VALUES (?, ?);";
		try (Connection connection = DriverManager.getConnection(dbConfig.schoolURL, dbConfig.schoolUsername,
				dbConfig.schoolPassword);
				PreparedStatement addingStatement = connection.prepareStatement(addStudentQuery)) {

			addingStatement.setString(1, firstName);
			addingStatement.setString(2, lastName);

			addingStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}

		System.out.println("New student " + firstName + " " + lastName + " is added to the database");
	}

	void deleteStudent(int id) {

		String deleteStudentQuery = "DELETE FROM school.students WHERE student_id = ?;";

		try (Connection connection = DriverManager.getConnection(dbConfig.schoolURL, dbConfig.schoolUsername,
				dbConfig.schoolPassword);
				PreparedStatement deleteStudentStatment = connection.prepareStatement(deleteStudentQuery)) {

			deleteStudentStatment.setInt(1, id);

			deleteStudentStatment.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}

		System.out.println("Student with the id " + id + " is deleted from the database");
	}

}
