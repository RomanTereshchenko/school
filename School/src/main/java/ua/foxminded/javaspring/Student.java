package ua.foxminded.javaspring;

import java.util.List;

import lombok.*;

@Data

public class Student {
	
	private int studentID;
	private int groupID;
	private String firstName;
	private String lastName;
	private List<Course> courses;
	
	public Student(int studentID, String firstName, String lastName) {
		super();
		this.studentID = studentID;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(int studentID, List<Course> courses) {
		super();
		this.studentID = studentID;
		this.courses = courses;
	}
	
	
	

}
