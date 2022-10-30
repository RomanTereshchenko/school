package ua.foxminded.javaspring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class GroupDAO {

	private DBConfig dbConfig = new DBConfig();

	void addGroupsToDB() {
		IntStream.rangeClosed(1, GroupGenerator.groups.size())
				.forEach(groupID -> addGroup(GroupGenerator.groups.get(groupID - 1).getGroupName()));
		System.out.println("Groups added to School database");
	}

	private void addGroup(String name) {

		String addGroupQuery = "INSERT INTO school.groups(group_name) VALUES(?);";

		try (Connection connection = DriverManager.getConnection(dbConfig.schoolURL, dbConfig.schoolUsername,
				dbConfig.schoolPassword);
				PreparedStatement addGroupStatment = connection.prepareStatement(addGroupQuery);) {

			addGroupStatment.setString(1, name);

			addGroupStatment.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}
		System.out.println("Group " + name + " added to the database");
	}

	List<String> findGroupsWithStudentsLessOrEqual(int studentsInGroup) {

		List<String> groupsWithStudentsLessOrEqual = new ArrayList<>();
		String selectGroupsWithConditionQuery = "SELECT group_name FROM "
				+ "(school.groups inner join school.students ON school.groups.group_id = school.students.group_id) "
				+ "group by school.groups.group_id HAVING COUNT (school.groups.group_id) <= ?";

		try (Connection connection = DriverManager.getConnection(dbConfig.schoolURL, dbConfig.schoolUsername,
				dbConfig.schoolPassword);
				PreparedStatement getgroupsWithConditionStatment = connection
						.prepareStatement(selectGroupsWithConditionQuery);) {

			getgroupsWithConditionStatment.setInt(1, studentsInGroup);
			ResultSet rs = getgroupsWithConditionStatment.executeQuery();
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
		
		for (Student student : StudentGenerator.students) {
			if (student.getGroupID() != 0) {
				addGroupIDToStudentInDB(student.getGroupID(), student.getFirstName(), student.getLastName());
			}
		}
		System.out.println("Students assigned to groups in School database");
	}

	private void addGroupIDToStudentInDB(int groupID, String studentFirstName, String studentLastName) {

		String assignGroupToStudentQuery = "UPDATE school.students SET group_id = ? WHERE first_name = ? AND last_name = ?;";

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
		System.out.println("Student " + studentFirstName + " " + studentLastName + " assigned to group with ID "
				+ groupID);
	}

}
