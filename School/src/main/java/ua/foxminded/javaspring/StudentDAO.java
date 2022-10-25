package ua.foxminded.javaspring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.IntStream;

public class StudentDAO {

	private DBConfig dbConfig = new DBConfig();
	private StudentGenerator studentGenerator;

	void addStudentsToDB() {
		System.out.println(studentGenerator.students.get(0).getFirstName());
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
	
	void addGroupIDToAllTheirStudentsInDB() {
		for (Student student : studentGenerator.students) {
			if (student.getGroupID() != 0) {
				addGroupIDToStudentInDB(student.getGroupID(), student.getFirstName(), student.getLastName());
			}
		}
		System.out.println("Students assigned to groups in School database");
	}
	
	private void addGroupIDToStudentInDB(int groupID, String studentFirstName, String studentLastName ) {

		String assignGroupToStudentQuery = 
				"UPDATE school.students SET group_id = ? WHERE first_name = ? AND last_name = ?;";

		try (Connection connection = DriverManager.getConnection(dbConfig.schoolURL, dbConfig.schoolUsername,
				dbConfig.schoolPassword);
				PreparedStatement assignGroupToStudentStatment = connection
						.prepareStatement(assignGroupToStudentQuery)) {

			assignGroupToStudentStatment.setInt(1, groupID);
			assignGroupToStudentStatment.setString(2, studentFirstName);
			assignGroupToStudentStatment.setString(3, studentLastName);

			assignGroupToStudentStatment.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}
		System.out.println("Student with ID " + studentFirstName + " " + studentLastName + " assigned to group with ID " + groupID);
	}

	void deleteStudent(int id) {

		String deleteStudentQuery = "DELETE FROM school.students WHERE student_id = ?;";

		try (Connection connection = DriverManager.getConnection(dbConfig.schoolURL, dbConfig.schoolUsername, dbConfig.schoolPassword);
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
