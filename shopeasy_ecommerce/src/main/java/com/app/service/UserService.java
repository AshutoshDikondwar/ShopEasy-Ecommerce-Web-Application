package com.app.service;

import java.nio.file.AccessDeniedException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.custom_exceptions.ErrorHandler;
import com.app.custom_exceptions.MalFormedTokenException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.custom_exceptions.TokenExpiredException;
import com.app.custom_exceptions.UserNotFoundException;
import com.app.dto.AuthRequest;
import com.app.dto.AuthResponse;
import com.app.dto.UpdateUserDTO;
import com.app.dto.UserDTO;

public interface UserService {

	String createUser(UserDTO user);

	public AuthResponse loginUser(AuthRequest loginDto, HttpServletResponse response, HttpSession session) throws UserNotFoundException;

	public String updateUser(String id, UpdateUserDTO updateDto) throws UserNotFoundException;

	public String deleteUser(String id) throws UserNotFoundException;

	public UserDTO getSingleUser(String id) throws UserNotFoundException;

	public List<UserDTO> findAllUser(String token, HttpSession session) throws AccessDeniedException,
			TokenExpiredException, MalFormedTokenException, ResourceNotFoundException, ErrorHandler;

	public String logout(HttpServletResponse res);
}
