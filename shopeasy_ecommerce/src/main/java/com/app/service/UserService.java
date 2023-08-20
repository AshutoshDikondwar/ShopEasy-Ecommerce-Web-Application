package com.app.service;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.LoginDTO;
import com.app.dto.UserDTO;

public interface UserService {

	String createUser(UserDTO user);
	public String loginUser(LoginDTO loginDto)throws ResourceNotFoundException;

}
