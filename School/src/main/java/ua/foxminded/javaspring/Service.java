package ua.foxminded.javaspring;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Service {
	
	String readCreatingTablesScript(String scriptFilePath) {

		List<String> scriptLines = new ArrayList<>();

		try (Stream<String> stream = Files.lines(Paths.get(scriptFilePath))) {

			scriptLines = stream.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return String.join("\n", scriptLines);

	}
	
//	String readFile(String file) {
//		
//	    BufferedReader reader = null;
//		try {
//			reader = new BufferedReader(new FileReader (file));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	    String         line = null;
//	    StringBuilder  stringBuilder = new StringBuilder();
//	    String         ls = System.getProperty("\n");
//
//	    try {
//	        while((line = reader.readLine()) != null) {
//	            stringBuilder.append(line);
//	            stringBuilder.append(ls);
//	        }
//	        reader.close();
//	    } 
//	    catch (IOException e) {
//			e.printStackTrace();
//		}
//	    
//        return stringBuilder.toString();
//	}
	

}
