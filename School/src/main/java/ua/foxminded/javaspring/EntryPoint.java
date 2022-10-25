package ua.foxminded.javaspring;

public class EntryPoint {
	private static DBConfig dbConfig = new DBConfig();

	public static void main(String[] args) {

//		Controller controller = new Controller();
//		
//		controller.startUp();
//		controller.menu();
		
		System.out.println(dbConfig.getProperty("url"));
		
//		File file = new File("resources/abc.txt");
//		String absolutePath = file.getAbsolutePath();
//		System.out.println(absolutePath);
//		
//		/School/src/main/resources/testFileResource.txt
//		/School/DBConfig.properties
		

	}

}
