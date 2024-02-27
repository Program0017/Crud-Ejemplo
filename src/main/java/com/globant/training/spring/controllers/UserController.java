package com.globant.training.spring.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globant.training.spring.DTO.UserGestionDto;
import com.globant.training.spring.entities.Role;
import com.globant.training.spring.entities.User;
import com.globant.training.spring.services.UserService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;	
	
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody UserGestionDto userGestionDto){
		User createdUser = userService.createUserWithDto(userGestionDto);
	    return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}
	
	@PostMapping("/{userId}/roles/{roleId}")
	public ResponseEntity<User> addUserRole(@PathVariable("userId")Integer userId, @PathVariable("roleId")Integer roleId){
		User user = userService.addUserRole(userId, roleId);
        return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody UserGestionDto userGestionDto) {
        User updatedUser = userService.updateUserWithDto(userId, userGestionDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
	
	@GetMapping
	 public ResponseEntity<Page<User>> getAllUserWithProfileAndAddress(
			 @RequestParam(required = false ,value="page" , defaultValue ="0") Integer page,
			 @RequestParam(required = false ,value="size" , defaultValue = "5") Integer size){
		
		 return new ResponseEntity< >(userService.getAllUsers(page, size),HttpStatus.OK);
	 }
	
	@GetMapping(value = "/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId) {
		return new ResponseEntity<User>(userService.getUserById(userId), HttpStatus.FOUND);
	}
	
	@GetMapping("/{userId}/roles")
	public ResponseEntity<List<Role>> getUserRoles(@PathVariable("userId") Integer userId) {
		List<Role> roles = userService.getUserRoles(userId);
        return new ResponseEntity<>(roles, HttpStatus.OK);
	}
	
	
	
	@PatchMapping("/toggle-status/{userId}")
	public ResponseEntity<User> toggleUserStatus(@PathVariable("userId") Integer userId){		
		return new ResponseEntity<User>(userService.toggleUserStatus(userId), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{userId}/roles/{roleId}")
	public ResponseEntity<User> deleteUserRoles(@PathVariable("userId")Integer userId, @PathVariable("roleId") Integer roleId){
		 User user = userService.removeRole(userId, roleId);
	        return new ResponseEntity<>(user, HttpStatus.OK);
	}
		
	@DeleteMapping("/{userId}")
	public ResponseEntity<User> deleteUser(@PathVariable("userId") Integer userId){
		userService.deleteUser(userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/edit/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody User user) {
		return new ResponseEntity<User>(userService.updateUser(userId,user),HttpStatus.ACCEPTED);
	}
	
}
