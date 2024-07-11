package com.austine.springsecurity.dto;

import lombok.Data;

@Data
public class SignUpRequest {
	private String firstName;
	private String lasttName;
	private String email;
	private String password;

}
