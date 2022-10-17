package ua.foxminded.javaspring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupDAO {
	
	GroupDAO groupDAO = new GroupDAO();	
	Random random = new Random();
	int nextUnassignedStudentID = 1;
	
	Properties dbProp = new Properties();
	String url = dbProp.getProperty("SCHOOL_DB_URL");
	String username = dbProp.getProperty("SCHOOL_DB_USERNAME");
	String password = dbProp.getProperty("SCHOOL_DB_PASSWORD");
	
	
	List<String> groups = IntStream.rangeClosed(1, 10)
			.mapToObj(number -> (new StringBuilder().append(((char) (random.nextInt(26) + 'a'))).append(
					((char) (random.nextInt(26) + 'a'))) + ("-" + random.nextInt(10) + random.nextInt(10)))
					.toString())
			.collect(Collectors.toList());
	
	void createGroups() {
		IntStream.rangeClosed(1, groups.size())
				.forEach(number -> addGroup(number, groups.get(number - 1)));
		System.out.println("Groups created");
	}
	
	void addGroup(int id, String name) {

		String addGroupQuery = "INSERT INTO school.groups VALUES(?, ?);";

		try (Connection conn = DriverManager.getConnection(url, username, password);
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
		IntStream.rangeClosed(1, groups.size()).forEach(groupDAO::buildOneGroup);
	}

	void buildOneGroup(int groupID) {

		int limitOfStudentsInGroup = ((random.nextInt(20)) + 10);
		int numberOfStudentsInGroup = 0;
		School school = new School();

		while (numberOfStudentsInGroup < limitOfStudentsInGroup) {
			school.assignStudentWithGroup(groupID, nextUnassignedStudentID);
			numberOfStudentsInGroup++;
			nextUnassignedStudentID++;
		}

	}


}
