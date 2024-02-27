package com.globant.training.spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.globant.training.spring.entities.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer>{

	public Optional<User> findByUsername(String username);

	public Optional<User> findByUsernameAndPassword(String username, String password);
	
	


}
