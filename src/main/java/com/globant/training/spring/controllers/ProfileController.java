package com.globant.training.spring.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.globant.training.spring.entities.Profile;
import com.globant.training.spring.entities.Role;
import com.globant.training.spring.entities.User;
import com.globant.training.spring.services.ProfileService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.web.bind.annotation.PutMapping;


@RequestMapping("/users/{userId}/profiles")
@RestController
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@PostMapping
	public ResponseEntity<Profile> createProfile(@PathVariable("userId") Integer userId, @RequestBody Profile profile){
		return new ResponseEntity<Profile>(profileService.create(userId, profile), HttpStatus.CREATED);
	} 
	
	@GetMapping("/{profileId}")
	public ResponseEntity<Profile> getById(
			@PathVariable("userId") Integer userId, 
			@PathVariable("profileId") Integer profileId){
		return new ResponseEntity<Profile>(profileService.getUserByProfileIdAndProfileId(userId,profileId), HttpStatus.OK);
	}
	
	@PutMapping("/{profileId}")
    public ResponseEntity<Profile> updateRole(@PathVariable("profileId") Integer profileId, @RequestBody Profile profile){
        return new ResponseEntity<>(profileService.editProfile(profileId, profile), HttpStatus.OK);
    }
	
	@DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfile(@PathVariable("profileId") Integer profileId){
		profileService.deleteRole(profileId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
	
}	
