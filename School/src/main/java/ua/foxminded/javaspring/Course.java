package ua.foxminded.javaspring;

import lombok.*;

@Data

public class Course {
	
	private int courseID;
	private String courseName;
	private String courseDescription;
	
	public Course(String courseName) {
		super();
		this.courseName = courseName;
	}

}
