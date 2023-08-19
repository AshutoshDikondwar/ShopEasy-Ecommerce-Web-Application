package com.app.collections;

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
	private String user_id;
	private String name;
	private String email;
	private String password;
	private Avatar avatar;
	private String role;

	public User(String user_id, String name, String email, String password, Avatar avatar, String role) {
		this.user_id = user_id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.avatar = avatar;
		this.role = "user";

	}
}
