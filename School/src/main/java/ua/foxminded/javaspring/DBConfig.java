package ua.foxminded.javaspring;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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