package com.example.HotelManagement.serviceAuth;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.HotelManagement.Repository.UserRepository;
import com.example.HotelManagement.dto.SignUpRequest;
import com.example.HotelManagement.dto.Userdto;
import com.example.HotelManagement.entity.User;
import com.example.HotelManagement.enums.UserRole;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

	private final UserRepository userRepository;
	
	@PostConstruct
	public void createAnAdminAccount() {
		Optional<User>adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
		if(adminAccount.isEmpty()) {
			User user = new User();
			user.setName("Admin");
			user.setEmail("admin@gmail.com");
			user.setUserRole(UserRole.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
			System.out.println("Admin account created successfully");
		}
		else {
			System.out.println("Admin account already exists!!");
		}
	}
	
	public Userdto createUser(SignUpRequest signUpRequest) {
		Optional<User> userExisted = userRepository.findFirstByEmail(signUpRequest.getEmail());
		if(userExisted.isPresent()) {

			System.out.println("user already exist with this email : " + signUpRequest.getEmail());
			throw new EntityExistsException("user already exist with this email : " + signUpRequest.getEmail());
		} 
		 

		User user = new User();
		user.setEmail(signUpRequest.getEmail());
		user.setName(signUpRequest.getName());
		user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
		user.setUserRole(UserRole.CUSTOMER);
		User createdUser = userRepository.save(user);
		System.out.println("user created successfully with email : " + signUpRequest.getEmail()
		                    + " name : " + signUpRequest.getName());
		
		return createdUser.getUserdto();
	}
}
