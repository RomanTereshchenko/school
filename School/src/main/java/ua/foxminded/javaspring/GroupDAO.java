package ua.foxminded.javaspring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class GroupDAO {

	int nextUnassignedStudentID = 1;
	Random random = new Random();
	
	String schoolURL = DBConfig.getProperty("url");
	String schoolUsername = DBConfig.getProperty("username");
	String schoolPassword = DBConfig.getProperty("password");
	

	
	void createGroups() {
		GroupGenerator groupGenerator = new GroupGenerator();
		IntStream.rangeClosed(1, groupGenerator.groups.size())
				.forEach(number -> addGroup(number, groupGenerator.groups.get(number - 1)));
		System.out.println("Groups created");
	}
	
	void addGroup(int id, String name) {

		String addGroupQuery = "INSERT INTO school.groups VALUES(?, ?);";

		try (Connection conn = DriverManager.getConnection(schoolURL, schoolUsername, schoolPassword);
				PreparedStatement addGroupStatm = conn.prepareStatement(addGroupQuery);) {
			System.out.println("Connected to database");

			addGroupStatm.setInt(1, id);
			addGroupStatm.setString(2, name);

			addGroupStatm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}

	}
	
	void buildAllGroups() {
		GroupGenerator groupGenerator = new GroupGenerator();
		GroupDAO groupDAO = new GroupDAO();
		IntStream.rangeClosed(1, groupGenerator.groups.size()).forEach(groupDAO ::buildOneGroup);
	}

	void buildOneGroup(int groupID) {

		int limitOfStudentsInGroup = ((random.nextInt(20)) + 10);
		int numberOfStudentsInGroup = 0;


		while (numberOfStudentsInGroup < limitOfStudentsInGroup) {
			assignStudentWithGroup(groupID, nextUnassignedStudentID);
			numberOfStudentsInGroup++;
			nextUnassignedStudentID++;
		}

	}
	
	void assignStudentWithGroup(int groupID, int unassignedStudentID) {

		String assignGroupToStudentQuery = "UPDATE school.students SET group_id = ? WHERE student_id = ? AND group_id IS NULL;";

		try (Connection conn = DriverManager.getConnection(schoolURL, schoolUsername, schoolPassword);
				PreparedStatement assignGroupToStudentStatm = conn.prepareStatement(assignGroupToStudentQuery)) {
			System.out.println("Connected to database");

			assignGroupToStudentStatm.setInt(1, groupID);
			assignGroupToStudentStatm.setInt(2, unassignedStudentID);

			assignGroupToStudentStatm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}
	}
	
	List<String> getGroups() {

		List<String> groupsFromSchool = new ArrayList<>();

		String getGroupsQuery = "SELECT * from school.groups;";

		try (Connection conn = DriverManager.getConnection(schoolURL, schoolUsername, schoolPassword);
				PreparedStatement getBookStatm = conn.prepareStatement(getGroupsQuery);
				ResultSet rs = getBookStatm.executeQuery();) {
			System.out.println("Connected to database");

			while (rs.next()) {
				groupsFromSchool.add(rs.getString("group_name"));
			}
		}

		catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}

		return groupsFromSchool;

	}
	
	void findGroupsWithStudentsLessOrEqual(int studentsInGroup) {
		createGroupsWithStudentsLessOrEqual(studentsInGroup);
		System.out.println(getGroupsWithStudentsLessOrEqual());
	}

	void createGroupsWithStudentsLessOrEqual(int studentsInGroup) {

		String groupsWithStudentsLessOrEqualQuery = "DROP TABLE IF EXISTS school.groups_students_count; "
				+ "CREATE TABLE school.groups_students_count (group_id, student_count)" + "AS "
				+ "SELECT group_id, COUNT (group_id) FROM school.students GROUP BY group_id; "
				+ "DELETE from school.groups_students_count WHERE student_count > ?;";

		try (Connection conn = DriverManager.getConnection(schoolURL, schoolUsername, schoolPassword);
				PreparedStatement groupsWithStudentsLessOrEqualStatm = conn
						.prepareStatement(groupsWithStudentsLessOrEqualQuery)) {
			System.out.println("Connected to database");

			groupsWithStudentsLessOrEqualStatm.setInt(1, studentsInGroup);
			groupsWithStudentsLessOrEqualStatm.executeUpdate();
			System.out.println("Groups with students less or equal " + studentsInGroup + " found");
		} catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
		}

	}

	List<String> getGroupsWithStudentsLessOrEqual() {

		List<String> groupsWithStudentsLessOrEqual = new ArrayList<>();
		String getGroupsWithStudentsLessOrEqualQuery = "SELECT school.groups.group_id, group_name FROM school.groups "
				+ "INNER JOIN school.groups_students_count ON school.groups.group_id = school.groups_students_count.group_id;";

		try (Connection conn = DriverManager.getConnection(schoolURL, schoolUsername, schoolPassword);
				PreparedStatement getgroupsWithStudentsLessOrEqualStatm = conn
						.prepareStatement(getGroupsWithStudentsLessOrEqualQuery);
				ResultSet rs = getgroupsWithStudentsLessOrEqualStatm.executeQuery();) {
			System.out.println("Connected to database");

			while (rs.next()) {
				groupsWithStudentsLessOrEqual.add(rs.getString("group_name"));
			}
		}

		catch (SQLException e) {
			System.out.println("Connection falure");
			e.printStackTrace();
		}

		return groupsWithStudentsLessOrEqual;

	}


}
