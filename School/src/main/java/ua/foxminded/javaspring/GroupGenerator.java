package ua.foxminded.javaspring;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupGenerator {

	private Random random = new Random();

	private List<String> groupNames = IntStream.rangeClosed(1, 10)
			.mapToObj(number -> (new StringBuilder().append(((char) (random.nextInt(26) + 'a')))
					.append(((char) (random.nextInt(26) + 'a'))) + ("-" + random.nextInt(10) + random.nextInt(10)))
					.toString())
			.collect(Collectors.toList());

	static List<Group> groups = new ArrayList<>();

	void generateGroups() {

		for (int i = 0; i < groupNames.size(); i++) {
			groups.add(new Group(i+1, groupNames.get(i)));
		}
		System.out.println("Groups generated");
	
	}

}
