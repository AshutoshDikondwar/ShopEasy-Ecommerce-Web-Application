package com.app.service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.collections.User;
import com.app.custom_exceptions.ErrorHandler;
import com.app.custom_exceptions.MalFormedTokenException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.custom_exceptions.TokenExpiredException;
import com.app.custom_exceptions.UserNotFoundException;
import com.app.dto.AuthRequest;
import com.app.dto.AuthResponse;
import com.app.dto.UpdateUserDTO;
import com.app.dto.UserDTO;
import com.app.jwt.SaveCookie;
import com.app.repository.UserRepository;
import com.app.util.JwtUtil;

import io.jsonwebtoken.Claims;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public AuthResponse loginUser(AuthRequest loginDto, HttpServletResponse response, HttpSession session) throws UserNotFoundException {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

		if (loginDto.getEmail() == null || loginDto.getPassword() == null) {
			throw new UserNotFoundException("Please Enter Email or Password");
		}

		User user = userRepo.findByEmail(loginDto.getEmail());
		if (user != null) {

			if (bcrypt.matches(loginDto.getPassword(), user.getPassword())) {
				final String jwt = jwtUtil.generateToken(user.getId());
				System.out.println("login user token= " + jwt);
				Optional<User> opUser = userRepo.findById(user.getId());
				session.setAttribute("user", opUser.get());
				return SaveCookie.sendToken(jwt, response);

//				return "Authenticated user";
			} else {
				throw new UserNotFoundException("Invalid Credentials");
			}
		}
		throw new UserNotFoundException("Invalid email or password");
	}

	@Override
	public String createUser(UserDTO userDto) {

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		User user = mapper.map(userDto, User.class);
		String encryptedPwd = bcrypt.encode(user.getPassword());
		user.setPassword(encryptedPwd);
		userRepo.save(user);

		return user.getName();
	}

	@Override
	public String updateUser(String id, UpdateUserDTO updateDto) throws UserNotFoundException {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));

		if (bcrypt.matches(updateDto.getOldPassword(), user.getPassword())) {
			if (bcrypt.matches(user.getPassword(), updateDto.getNewPassword())) {
				throw new UserNotFoundException("Old password and New Password are same");
			} else {
				user.setName(updateDto.getName());
				user.setPassword(bcrypt.encode(updateDto.getNewPassword()));
				userRepo.save(user);
			}

		} else {
			throw new UserNotFoundException("Invalid Credentials");
		}
		return "user updated successfully";
	}

	// ADMIN
	@Override
	public String deleteUser(String id) throws UserNotFoundException {
		User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
		userRepo.deleteById(id);
		return "User deleted successfully";

	}

	// ADMIN
	@Override
	public UserDTO getSingleUser(String id) throws UserNotFoundException {
		User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));

		UserDTO userDto = mapper.map(user, UserDTO.class);

		return userDto;
	}

//	ADMIN
	@Override
	public List<UserDTO> findAllUser(String tokenjwt, HttpSession session) throws AccessDeniedException,
			TokenExpiredException, MalFormedTokenException, ResourceNotFoundException, ErrorHandler {

		// Authenticate user
		if (tokenjwt == null) {
			throw new ResourceNotFoundException("Please login to access this resources");
		}
		final Claims decodeDate = jwtUtil.verify(tokenjwt);
		String user_id = decodeDate.getSubject();
		Optional<User> opUser = userRepo.findById(user_id);
		session.setAttribute("user", opUser.get());

		// Authorize user("Admin")
		User storedUser = (User) session.getAttribute("user");
		String userRole = storedUser.getRole();
		if (userRole.equals("Admin")) {

		} else {
			throw new ErrorHandler(storedUser + " is not allowed to access this resource");
		}

		List<UserDTO> userDto = new ArrayList<>();
		List<User> user = userRepo.findAll();
		user.forEach(i -> {
			UserDTO u = mapper.map(i, UserDTO.class);
			userDto.add(u);
		});

		return userDto;
	}

	@Override
	public String logout(HttpServletResponse res) {
		Cookie cookie = new Cookie("token", null);
		cookie.setMaxAge(0);
		res.addCookie(cookie);
		return "Logged out Successfully";
	}

}
