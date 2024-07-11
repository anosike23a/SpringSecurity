package com.austine.springsecurity.service;

import com.austine.springsecurity.dto.JwtAuthenticationResponse;
import com.austine.springsecurity.dto.RefreshTokenRequest;
import com.austine.springsecurity.dto.SignInRequest;
import com.austine.springsecurity.dto.SignUpRequest;
import com.austine.springsecurity.entity.User;

public interface AuthenticationService {
	
	User signUp(SignUpRequest signUpRequest);
	JwtAuthenticationResponse sigIn(SignInRequest signInRequuest);
	
	JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
