package ua.foxminded.javaspring;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfig {

	static String getProperty(String propertyName) {
		
		String propertyValue = null;

		try (InputStream input = new FileInputStream("DBConfig.properties")) {

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