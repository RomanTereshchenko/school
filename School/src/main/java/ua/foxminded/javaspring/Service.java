package ua.foxminded.javaspring;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Service {
	
	static String readCreatingTablesScript(String scriptFilePath) {

		List<String> scriptLines = new ArrayList<>();

		try (Stream<String> stream = Files.lines(Paths.get(scriptFilePath))) {

			scriptLines = stream.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return String.join("\n", scriptLines);

	}

}
