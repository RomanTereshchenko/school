package ua.foxminded.javaspring;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConfig {

	private static String pathToProperties = "src/main/resources/DBConfig.properties";
	String schoolURL;
	String schoolUsername;
	String schoolPassword;

	public DBConfig() {
		super();
		schoolURL = getProperty("url");
		schoolUsername = getProperty("username");
		schoolPassword = getProperty("password");

		if (schoolURL == null || schoolUsername == null || schoolPassword == null) {
			throw new RuntimeException("Failed to connect to School database");
		}
	}

	Connection getConnection() throws SQLException {
		return DriverManager.getConnection(schoolURL, schoolUsername, schoolPassword);
	}

	static String getProperty(String propertyName) throws RuntimeException {

		String propertyValue = null;

		try (InputStream input = new FileInputStream(pathToProperties)) {

			Properties dbProp = new Properties();

			dbProp.load(input);

			propertyValue = dbProp.getProperty(propertyName);

		} catch (IOException e) {
			System.out.println("Database Properties not found");
			e.printStackTrace();
		}

		return propertyValue;

	}
}