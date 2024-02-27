package com.globant.training.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.globant.training.spring.DTO.UserGestionDto;
import com.globant.training.spring.entities.Address;
import com.globant.training.spring.entities.Profile;
import com.globant.training.spring.entities.Role;
import com.globant.training.spring.entities.User;
import com.globant.training.spring.repositories.AddressRepository;
import com.globant.training.spring.repositories.ProfileRepository;
import com.globant.training.spring.repositories.RoleRepository;
import com.globant.training.spring.repositories.UserRepository;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	public Page<User> getAllUsers(Integer page, Integer size){		
		return userRepository.findAll(PageRequest.of(page, size));
	}
	
	public User getUserById(Integer userId) {
		return userRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %d not found", userId)));		 
	}
	
	
	@Transactional
	public User createUserWithDto(UserGestionDto userGestionDto) {
		User user = new User();
		user.setUsername(userGestionDto.getUsername());
		user.setPassword(userGestionDto.getPassword());
		user.setActive(userGestionDto.isActive());
		user = userRepository.save(user);
		
		Profile profile = new Profile();
		profile.setFirstname(userGestionDto.getFirstname());
		profile.setLastname(userGestionDto.getLastname());
		profile.setBirthDate(userGestionDto.getBirthDate());
		profile.setUser(user);
		profile = profileRepository.save(profile);
		
		
		Address address = new Address();
		
		address.setStreet(userGestionDto.getStreet());
		address.setNumber(userGestionDto.getNumber());
		address.setCity(userGestionDto.getCity());
		address.setProfile(profile);
		
		addressRepository.save(address);		
		return user;
	}
	
	@Transactional
    public User updateUserWithDto(Integer userId, UserGestionDto userGestionDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setUsername(userGestionDto.getUsername());
        user.setPassword(userGestionDto.getPassword());
        user.setActive(userGestionDto.isActive());
        
        
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        profile.setFirstname(userGestionDto.getFirstname());
        profile.setLastname(userGestionDto.getLastname());
        profile.setBirthDate(userGestionDto.getBirthDate());
        
        profileRepository.save(profile); 
        
        Address address = addressRepository.findByProfile(profile)
        	    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));
        
        
        address.setCity(userGestionDto.getCity());
        address.setStreet(userGestionDto.getStreet());
        address.setNumber(userGestionDto.getNumber());
        
        addressRepository.save(address);
        return userRepository.save(user);
    }
	
	public User toggleUserStatus(Integer userId) {
		User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %d not found", userId)));
        user.setActive(!user.isActive());
        return userRepository.save(user);
	}
	
	public User addUserRole(Integer userId, Integer roleId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		Role role = roleRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		user.addRole(role);
		return userRepository.save(user);
	}
	
	public User removeRole(Integer userId, Integer roleId){
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		Role role = roleRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		user.removeRole(role);
		return userRepository.save(user);
	}
	
	public List<Role> getUserRoles(Integer userId){
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		return new ArrayList<>(user.getRoles());
	}
	
	
	public User updateUser(Integer userId, User user) {
		Optional<User> result = userRepository.findById(userId);
		if (result.isPresent()) {
			return userRepository.save(user);
		}else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s doesn't exist", userId));
		}
	}
	
	public void deleteUser(Integer userId) {
		Optional<User> result = userRepository.findById(userId);
		if (result.isPresent()) {
			userRepository.delete(result.get());
		}else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s doesn't exist", userId));

		}
	}

	


	
}


