package com.austine.springsecurity.service.impl;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.austine.springsecurity.dto.JwtAuthenticationResponse;
import com.austine.springsecurity.dto.RefreshTokenRequest;
import com.austine.springsecurity.dto.SignInRequest;
import com.austine.springsecurity.dto.SignUpRequest;
import com.austine.springsecurity.entity.Role;
import com.austine.springsecurity.entity.User;
import com.austine.springsecurity.repository.UserRepository;
import com.austine.springsecurity.service.AuthenticationService;
import com.austine.springsecurity.service.JWTService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;
	
	
	public User signUp(SignUpRequest signUpRequest) {
		
		User user = new User();
		user.setFirstname(signUpRequest.getFirstName());
		user.setSecondname(signUpRequest.getLasttName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setRole(Role.USER);
		
		return userRepository.save(user);
		
	}
	
	public JwtAuthenticationResponse sigIn(SignInRequest signInRequuest) {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequuest.getEmail(),signInRequuest.getPassword()));
		var user = userRepository.findByEmail(signInRequuest.getEmail()).orElseThrow(()-> new IllegalArgumentException("Invalid email or Password"));
		var jwt = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
		
		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
		jwtAuthenticationResponse.setToken(jwt);
		jwtAuthenticationResponse.setRefreshToken(refreshToken);
		
		return jwtAuthenticationResponse;
		
	}
	
	public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		
		String  userEmail = jwtService.extractUserName(refreshTokenRequest.getRefresToken());
		User user = userRepository.findByEmail(userEmail).orElseThrow();
		if(jwtService.isTokenValid(refreshTokenRequest.getRefresToken(), user)) {
			var jwt = jwtService.generateToken(user);
			
			JwtAuthenticationResponse  jwtAuthenticationResponse = new JwtAuthenticationResponse();
			jwtAuthenticationResponse.setToken(jwt);
			jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getRefresToken());
			
			return jwtAuthenticationResponse;
		}
		
		return null;
				
	}
}
