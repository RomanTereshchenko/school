package ua.foxminded.javaspring;

import lombok.*;
@Data


public class Group {

	private int groupID;
	private String groupName;
	public Group(String groupName) {
		super();
		this.groupName = groupName;
	}
}
