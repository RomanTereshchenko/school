package ua.foxminded.javaspring;

import lombok.*;

@Data

public class Student {
	
	private int studentID;
	private int groupID;
	private String firstName;
	private String lastName;
	
	public Student(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(int groupID, String firstName, String lastName) {
		super();
		this.groupID = groupID;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	
	

}
