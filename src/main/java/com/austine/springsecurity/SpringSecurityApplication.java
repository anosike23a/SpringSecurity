package com.austine.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.austine.springsecurity.entity.Role;
import com.austine.springsecurity.entity.User;
import com.austine.springsecurity.repository.UserRepository;

@SpringBootApplication
public class SpringSecurityApplication implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}
	
	  @Override
	  public void run(String... args) {
		User adminAccount = userRepository.findByRole(Role.USER);
		if(null == adminAccount) {
			User user = new User();
			user.setFirstname("user");
			user.setSecondname("user");
			user.setEmail("user@gmail.com");
			user.setRole(Role.USER
					);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}
		
		
		
	}

}
