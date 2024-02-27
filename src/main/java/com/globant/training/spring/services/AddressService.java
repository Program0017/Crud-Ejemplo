package com.globant.training.spring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.globant.training.spring.entities.Address;
import com.globant.training.spring.entities.Profile;
import com.globant.training.spring.repositories.AddressRepository;
import com.globant.training.spring.repositories.ProfileRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ProfileRepository profileRepository;

	public List<Address> findAddressesByprofileIdAndUserId(Integer userId, Integer profileId) {
		return addressRepository.findByProfileId(profileId, userId);
	}

	public Address create(Integer userId, Integer profileId, Address address) {
		Optional<Profile> result = profileRepository.findByUserIdAndProfileId(userId, profileId);
		if (result.isPresent()) {
			address.setProfile(result.get());
			return addressRepository.save(address);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("Address not found for user $d and profile %d ", userId, profileId));
		}

	}

	public Address updateAddress(Integer addressid, Address address) {
		Optional<Address> result = addressRepository.findById(addressid);
		if (result.isPresent()) {
			return addressRepository.save(address);
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Address $d not fournd", addressid));
		}
		
	}

	public void deleteAddress(Integer addressid) {
		Optional<Address> result = addressRepository.findById(addressid);
		if (result.isPresent()) {
			addressRepository.delete(result.get());
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Address $d not fournd", addressid));
		}
	}



}
