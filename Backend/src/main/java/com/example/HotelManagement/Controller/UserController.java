package com.example.HotelManagement.Controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HotelManagement.Repository.UserRepository;
import com.example.HotelManagement.dto.AuthenticationRequest;
import com.example.HotelManagement.dto.Authenticationresponse;
import com.example.HotelManagement.dto.SignUpRequest;
import com.example.HotelManagement.dto.Userdto;
import com.example.HotelManagement.entity.User;
import com.example.HotelManagement.serviceAuth.AuthServiceImpl;
import com.example.HotelManagement.serviceAuth.UserService;
import com.example.HotelManagement.util.JwtUtil;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
	private final AuthServiceImpl authService;
	private final AuthenticationManager authManager;
	private final UserRepository userRepo;
	private final JwtUtil util;
	private final UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> sugnUpUser( @RequestBody SignUpRequest signUp){
		try {
			Userdto createdUser = authService.createUser(signUp);
			return new ResponseEntity(createdUser, HttpStatus.OK);
		}catch(EntityExistsException ex) {
			return new ResponseEntity<>("user already exists", HttpStatus.OK);
		}catch(Exception ex) {
			return new ResponseEntity<>("user is not created", HttpStatus.BAD_REQUEST);

		}
		
	}
	
//	@PostMapping("/login")
//	public Authenticationresponse loginUser(@RequestBody AuthenticationRequest login) {
//		try {
//			Authenticationresponse user = authService.loginUser(login);
//			return user;
//		}catch(Exception ex) {
//			return ex;
//		}
//	}
	@PostMapping("/login")
	public Authenticationresponse createAuthenticationToken(@RequestBody AuthenticationRequest request) {
		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		}
		catch(BadCredentialsException e) {
			throw new BadCredentialsException("incorrect username or password");
		}
		final UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
		Optional<User> optionalUser = userRepo.findFirstByEmail(userDetails.getUsername());
		final String jwt = util.generateToken(userDetails);
		Authenticationresponse res = new Authenticationresponse();
		if(optionalUser.isPresent()) {
			res.setJwt(jwt);
			res.setUserId(optionalUser.get().getId());
			res.setUserRole(optionalUser.get().getUserRole());
		}
		return res;
		
	}

}
