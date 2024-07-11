package com.austine.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.austine.springsecurity.entity.Role;
import com.austine.springsecurity.service.UserService;


import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguartion {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final UserService userService;
	
	@Bean
	public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests(request ->request.requestMatchers("/api/v1/**")
		.permitAll()
		.requestMatchers("/api/v1/admin").hasAnyAuthority(Role.ADMIN.name())
				.requestMatchers("/api/v1/user").hasAnyAuthority(Role.USER.name())
				.requestMatchers("/api/v1/manager").hasAnyAuthority(Role.MANAGER.name())
				.anyRequest().authenticated())
		.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authenticationProvider(authenticationProvider()).addFilterBefore(
		jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
		
	}
	@Bean
	public AuthenticationProvider authenticationProvider() throws Exception{
		
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userService.userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		
		return new BCryptPasswordEncoder();
		
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception{
		return config.getAuthenticationManager();
	}
	

}
