package ua.foxminded.javaspring;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupGenerator {

	private Random random = new Random();

	List<Group> generateNGroups(int countToGenerate) {

		List<Group> groupsLocal = new ArrayList<>();
		
		for (int i = 0; i < countToGenerate; i++) {
			Group group = new Group();
			group.setGroupName(generateGroupName());
			group.setGroupID(i+1);
			groupsLocal.add(group);
		}
		
		System.out.println("Groups generated");
		return groupsLocal;
	}

	private String generateGroupName() {
		return (new StringBuilder().append(((char) (random.nextInt(26) + 'a')))
				.append(((char) (random.nextInt(26) + 'a'))) + ("-" + random.nextInt(10) + random.nextInt(10)))
				.toString();
	}

}
