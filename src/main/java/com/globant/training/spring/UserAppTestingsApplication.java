package com.globant.training.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.javafaker.Faker;
import com.globant.training.spring.entities.Profile;
import com.globant.training.spring.entities.User;
import com.globant.training.spring.repositories.ProfileRepository;
import com.globant.training.spring.repositories.UserRepository;

@SpringBootApplication
public class UserAppTestingsApplication implements ApplicationRunner {
	
	@Autowired
	private Faker faker;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(UserAppTestingsApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		
		for (int i = 0; i < 10; i++) {
			
			User user = new User();
			user.setUsername(faker.name().username());
			user.setPassword(faker.harryPotter().character());
			user.setActive(true);
			
			userRepository.save(user);
			
			
			
		}
		
	}

}
