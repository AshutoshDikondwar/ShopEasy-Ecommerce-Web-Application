package com.app.service;

import java.util.List;

import com.app.custom_exceptions.UserNotFoundException;
import com.app.dto.AuthRequest;
import com.app.dto.UpdateUserDTO;
import com.app.dto.UserDTO;

public interface UserService {

	String createUser(UserDTO user);

	public String loginUser(AuthRequest loginDto) throws UserNotFoundException;

	public String updateUser(String id, UpdateUserDTO updateDto) throws UserNotFoundException;

	public String deleteUser(String id) throws UserNotFoundException;

	public UserDTO getSingleUser(String id) throws UserNotFoundException;

	public List<UserDTO> findAllUser();

}
