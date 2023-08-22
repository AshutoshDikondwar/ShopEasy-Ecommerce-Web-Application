package com.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.collections.User;
import com.app.custom_exceptions.UserNotFoundException;
import com.app.dto.AuthRequest;
import com.app.dto.AuthResponse;
import com.app.dto.UpdateUserDTO;
import com.app.dto.UserDTO;
import com.app.jwt.UserDetailsImpl;
import com.app.service.UserService;
import com.app.util.JwtUtil;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsImpl userDetailsImpl;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public String loginUser(@RequestBody AuthRequest loginDto, HttpSession session) throws UserNotFoundException {
		return userService.loginUser(loginDto);

	}

	@PostMapping("/create")
	public String createUser(@Valid @RequestBody UserDTO user, HttpSession session) {
		userService.createUser(user);
		return user.getName();

	}

	@PutMapping("/update/{id}")
	public String updateUser(@PathVariable String id, @RequestBody UpdateUserDTO updateUser)
			throws UserNotFoundException {
		return userService.updateUser(id, updateUser);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable String id) throws UserNotFoundException {
		return userService.deleteUser(id);
	}

	@GetMapping("/{id}")
	public UserDTO getSingleUser(@PathVariable String id) throws UserNotFoundException {
		return userService.getSingleUser(id);
	}

	@GetMapping("/all")
	public List<UserDTO> findAllUser() {
		return userService.findAllUser();
	}

	@PostMapping("/authenticate")
	public AuthResponse createAuthToken(@RequestBody AuthRequest authRequest, HttpServletResponse response)
			throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect Username or Password");
		} catch (DisabledException d) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is Not Created, Register a User first");
			return null;
		}
		final UserDetails userDetails = userDetailsImpl.loadUserByUsername(authRequest.getEmail());
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());
		return new AuthResponse(jwt);

	}

}