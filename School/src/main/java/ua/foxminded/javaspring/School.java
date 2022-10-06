package ua.foxminded.javaspring;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class School {

	static String pathToTablesCreatingFile = "scriptTest.txt";

	public static void main(String[] args) {

		System.out.println(readCreatingTablesScript(pathToTablesCreatingFile));
		createTables();

	}

	static void createTables() {

		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
				"1234");
				PreparedStatement st = conn.prepareStatement(readCreatingTablesScript(pathToTablesCreatingFile))) {
			System.out.println("Connected to database");
			st.executeUpdate();
			System.out.println("Tables created");
		} catch (SQLException e) {
			System.out.println("Connection failure");
			e.printStackTrace();
		}

	}

	private static String readCreatingTablesScript(String scriptFilePath) {
		List<String> scriptLines = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(scriptFilePath))) {

			scriptLines = stream.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return String.join("\n", scriptLines);

	}

}
