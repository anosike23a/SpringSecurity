package com.austine.springsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.austine.springsecurity.dto.JwtAuthenticationResponse;
import com.austine.springsecurity.dto.RefreshTokenRequest;
import com.austine.springsecurity.dto.SignInRequest;
import com.austine.springsecurity.dto.SignUpRequest;
import com.austine.springsecurity.entity.User;
import com.austine.springsecurity.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private AuthenticationService authenticationService;
	
	@PostMapping("/signup")
	public ResponseEntity<User> signUp(@RequestBody SignUpRequest signupRequest){
		
		return ResponseEntity.ok(authenticationService.signUp(signupRequest));
	}
	
	@PostMapping("/signIn")
	public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest){
		
		return ResponseEntity.ok(authenticationService.sigIn(signInRequest));
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
		
		return ResponseEntity.ok(authenticationService.refreshToken
				(refreshTokenRequest));
	}

}
