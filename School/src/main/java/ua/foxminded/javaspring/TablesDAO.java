package ua.foxminded.javaspring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TablesDAO {

	private DBConfig dbConfig = new DBConfig();
	private String pathToSchemaAndTablesCreatingFile = "src/main/resources/createScholSchema&Tables.sql";
	private Service service = new Service();

	void createSchemaAndTables() {

		try (Connection connection = dbConfig.getConnection();
				PreparedStatement createStatement = connection
						.prepareStatement(service.readCreatingTablesScript(pathToSchemaAndTablesCreatingFile))) {

			createStatement.executeUpdate();
			System.out.println("Tables created");

		} catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
		}

	}

}
