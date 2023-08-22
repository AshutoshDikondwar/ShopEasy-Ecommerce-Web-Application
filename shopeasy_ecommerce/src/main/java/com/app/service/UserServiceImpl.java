package com.app.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.collections.User;
import com.app.custom_exceptions.UserNotFoundException;
import com.app.dto.AuthRequest;
import com.app.dto.UpdateUserDTO;
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
	public String loginUser(AuthRequest loginDto) throws UserNotFoundException {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

		User user = userRepo.findByEmail(loginDto.getEmail());
		if (user != null) {

			if (bcrypt.matches(loginDto.getPassword(), user.getPassword())) {
				return "Authenticated user";
			} else {
				throw new UserNotFoundException("Invalid Credentials");
			}
		}
		throw new UserNotFoundException("Invalid Credentials");
	}

	@Override
	public String createUser(UserDTO userDto) {

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		User user = mapper.map(userDto, User.class);
		String encryptedPwd = bcrypt.encode(user.getPassword());
		user.setPassword(encryptedPwd);
//		 userRepo.save(user);
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

	@Override
	public String deleteUser(String id) throws UserNotFoundException {
		User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
		userRepo.deleteById(id);
		return "User deleted successfully";

	}

	@Override
	public UserDTO getSingleUser(String id) throws UserNotFoundException {
		User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));

		UserDTO userDto = mapper.map(user, UserDTO.class);

		return userDto;
	}

	@Override
	public List<UserDTO> findAllUser() {
		List<UserDTO> userDto = new ArrayList<>();
		List<User> user = userRepo.findAll();
		user.forEach(i -> {
			UserDTO u = mapper.map(i, UserDTO.class);
			userDto.add(u);
		});
		return userDto;
	}

}
