package ua.foxminded.javaspring;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupGenerator {
	
	private Random random = new Random();
	
	private List<String> groupNames = IntStream.rangeClosed(1, 10)
			.mapToObj(number -> (new StringBuilder().append(((char) (random.nextInt(26) + 'a'))).append(
					((char) (random.nextInt(26) + 'a'))) + ("-" + random.nextInt(10) + random.nextInt(10)))
					.toString())
			.collect(Collectors.toList());
	
	List<Group> groups = new ArrayList<>();

	List<Group> generateGroups() {
		
		for (String groupName : groupNames) {
			groups.add(new Group (groupName));
		}
		System.out.println("Groups generated");
		return groups;
	}

}
