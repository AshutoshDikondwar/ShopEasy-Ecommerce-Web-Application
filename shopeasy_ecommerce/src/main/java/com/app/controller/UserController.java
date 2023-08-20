package com.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.LoginDTO;
import com.app.dto.UserDTO;
import com.app.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public String loginUser(@RequestBody LoginDTO loginDto) throws ResourceNotFoundException {
		return userService.loginUser(loginDto);
	}
	
	@PostMapping
	public String createUser(@Valid @RequestBody UserDTO user) {
		return userService.createUser(user);
	
	}
	
}
