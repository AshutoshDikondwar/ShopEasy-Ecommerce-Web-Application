package com.app.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@Document(collation = "user")
public class User {
	@Id
	private String userName;
	private String name;
	private String email;
	private String password;
	private Avatar avatar;
	private String role;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String userName, String name, String email, String password, Avatar avatar, String role) {
		this.userName = userName;
		this.name = name;
		this.email = email;
		this.password = password;
		this.avatar = avatar;
		this.role = "user";

	}
}
