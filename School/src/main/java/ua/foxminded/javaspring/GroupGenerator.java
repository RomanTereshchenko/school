package ua.foxminded.javaspring;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupGenerator {
	
	
	Random random = new Random();
	
	List<String> groups = IntStream.rangeClosed(1, 10)
			.mapToObj(number -> (new StringBuilder().append(((char) (random.nextInt(26) + 'a'))).append(
					((char) (random.nextInt(26) + 'a'))) + ("-" + random.nextInt(10) + random.nextInt(10)))
					.toString())
			.collect(Collectors.toList());

}
