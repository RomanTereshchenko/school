package ua.foxminded.javaspring;

import lombok.*;

@Data
@AllArgsConstructor

public class Course {
	
	private int courseID;
	private String courseName;
	private String courseDescription;
	public Course(int courseID, String courseName) {
		super();
		this.courseID = courseID;
		this.courseName = courseName;
	}
	
}
