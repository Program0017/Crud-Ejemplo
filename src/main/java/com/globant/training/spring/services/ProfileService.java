package com.globant.training.spring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.globant.training.spring.entities.Profile;
import com.globant.training.spring.entities.User;
import com.globant.training.spring.repositories.ProfileRepository;
import com.globant.training.spring.repositories.UserRepository;

@Service
public class ProfileService {

	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Profile create(Integer userId, Profile profile){
		Optional<User> result = userRepository.findById(userId);
		if (result.isPresent()){
			profile.setUser(result.get());
			return profileRepository.save(profile);
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %d not found ", userId));
		}
	}

	public Profile getUserByProfileIdAndProfileId(Integer userId, Integer profileId) {
        return profileRepository.findByUserIdAndProfileId(userId, profileId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                        String.format("Profile not found for user %d and profile %d", userId, profileId)));
 
	}

	public Profile editProfile(Integer profileId, Profile profile) {
		Optional<Profile> result = profileRepository.findById(profileId);
		if (result.isPresent()) {
			return profileRepository.save(profile);
		}else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile %s doesn't exist", profileId));

		}
	}

	public void deleteRole(Integer profileId) {
		Optional<Profile> result = profileRepository.findById(profileId);
		if (result.isPresent()) {
			profileRepository.delete(result.get());
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile %s doesn't exist", profileId));
		}
		
	}

}
