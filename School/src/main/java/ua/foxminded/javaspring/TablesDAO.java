package ua.foxminded.javaspring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TablesDAO {
	
	String pathToSchemaAndTablesCreatingFile = "createScholSchema&Tables.sql";
	
	String schoolURL = DBConfig.getProperty("url");
	String schoolUsername = DBConfig.getProperty("username");
	String schoolPassword = DBConfig.getProperty("password");
	
	void createSchemaAndTables() {

		try (Connection conn = DriverManager.getConnection(schoolURL, schoolUsername, schoolPassword);
				PreparedStatement st = conn
						.prepareStatement(Service.readCreatingTablesScript(pathToSchemaAndTablesCreatingFile))) {
			System.out.println("Connected to database");
			st.executeUpdate();
			System.out.println("Tables created");
		} catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
		}

	}

}
