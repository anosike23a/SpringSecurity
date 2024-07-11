package com.austine.springsecurity.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public interface JWTService {
	
	String extractUserName(String token);
	String generateToken(UserDetails userDetails);	
	boolean isTokenValid(String token, UserDetails userDetails) ;
	String generateRefreshToken(Map<String ,Object> extractClaims, UserDetails userDetails);

}
