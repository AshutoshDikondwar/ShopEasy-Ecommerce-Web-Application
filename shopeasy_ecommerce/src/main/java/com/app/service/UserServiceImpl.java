package com.app.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.collections.User;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.LoginDTO;
import com.app.dto.UserDTO;
import com.app.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public String loginUser(LoginDTO loginDto) throws ResourceNotFoundException {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

		Optional<User> opUser = userRepo.findById(loginDto.getUserName());
		if (opUser.isPresent()) {
			User user = opUser.get();
			if (bcrypt.matches(loginDto.getPassword(), user.getPassword())) {
				return "Authenticated user";
			} else {
				return "Invalid Credentials";
			}
		}

		return "User not found";
	}

	@Override
	public String createUser(UserDTO userDto) {

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		User user = mapper.map(userDto, User.class);
		String encryptedPwd = bcrypt.encode(user.getPassword());
		user.setPassword(encryptedPwd);
		return userRepo.save(user).getName();
	}

}
