package ua.foxminded.javaspring;

public class EntryPoint {
	
	public static void main(String[] args) {
		
		Controller controller = new Controller();
		
		controller.startUp();
		controller.menu();

	}

}
