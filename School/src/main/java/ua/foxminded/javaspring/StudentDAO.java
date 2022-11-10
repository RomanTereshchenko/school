package ua.foxminded.javaspring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentDAO {

	private DBConfig dbConfig = new DBConfig();
	
	void addStudentsToDB() {
		
		Controller.students.forEach(student -> addStudent((student.getFirstName()), (student.getLastName())));
		System.out.println("Students added to School database");
	}

	void addStudent(String firstName, String lastName) {

		String insertQuery = "INSERT INTO school.students (first_name, last_name) VALUES (?, ?);";
		try (Connection connection = dbConfig.getConnection();
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

			insertStatement.setString(1, firstName);
			insertStatement.setString(2, lastName);

			insertStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
		}

		System.out.println("New student " + firstName + " " + lastName + " is added to the database");
	}

	void deleteStudentFromDB(int id) {

		String deleteQuery = "DELETE FROM school.students WHERE student_id = ?;";

		try (Connection connection = dbConfig.getConnection();
				PreparedStatement deleteStatment = connection.prepareStatement(deleteQuery)) {

			deleteStatment.setInt(1, id);

			deleteStatment.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
		}

		System.out.println("Student with the id " + id + " is deleted from the database");
	}

}
