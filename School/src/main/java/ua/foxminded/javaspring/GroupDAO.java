package ua.foxminded.javaspring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO {

	private DBConfig dbConfig = new DBConfig();

	void addGroupsToDB() {

		Controller.groups.forEach(group -> addGroup(group.getGroupName()));
		System.out.println("Groups added to School database");
	}

	private void addGroup(String name) {

		String insertQuery = "INSERT INTO school.groups(group_name) VALUES(?);";

		try (Connection connection = DriverManager.getConnection(dbConfig.schoolURL, dbConfig.schoolUsername,
				dbConfig.schoolPassword);
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery);) {

			insertStatement.setString(1, name);

			insertStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
		}
		System.out.println("Group " + name + " added to the database");
	}

	List<String> selectGroupsByStudentsCount(int studentsCount) {

		List<String> groupsWithStudentsLessOrEqual = new ArrayList<>();
		String selectQuery = "SELECT group_name FROM "
				+ "(school.groups inner join school.students ON school.groups.group_id = school.students.group_id) "
				+ "group by school.groups.group_id HAVING COUNT (school.groups.group_id) <= ?";

		try (Connection connection = DriverManager.getConnection(dbConfig.schoolURL, dbConfig.schoolUsername,
				dbConfig.schoolPassword);
				PreparedStatement selectStatement = connection
						.prepareStatement(selectQuery);) {

			selectStatement.setInt(1, studentsCount);
			ResultSet rs = selectStatement.executeQuery();
			while (rs.next()) {
				groupsWithStudentsLessOrEqual.add(rs.getString("group_name"));
			}
		}

		catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
		}
		return groupsWithStudentsLessOrEqual;
	}

	void addGroupIDToAllTheirStudentsInDB() {

		for (Student student : Controller.students) {
			if (student.getGroupID() != 0) {
				addGroupIDToStudentInDB(student.getGroupID(), student.getFirstName(), student.getLastName());
			}
		}
		System.out.println("Students assigned to groups in School database");
	}

	private void addGroupIDToStudentInDB(int groupID, String studentFirstName, String studentLastName) {

		String updateQuery = "UPDATE school.students SET group_id = ? WHERE first_name = ? AND last_name = ?;";

		try (Connection connection = DriverManager.getConnection(dbConfig.schoolURL, dbConfig.schoolUsername,
				dbConfig.schoolPassword);
				PreparedStatement updateStatement = connection
						.prepareStatement(updateQuery)) {

			updateStatement.setInt(1, groupID);
			updateStatement.setString(2, studentFirstName);
			updateStatement.setString(3, studentLastName);

			updateStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
		}
		System.out.println(
				"Student " + studentFirstName + " " + studentLastName + " assigned to group with ID " + groupID);
	}

}
