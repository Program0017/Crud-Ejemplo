package com.globant.training.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.globant.training.spring.entities.Address;
import com.globant.training.spring.services.AddressService;
import com.globant.training.spring.services.RoleService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/users/{userId}/profiles/{profileId}/addresses")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	@GetMapping
	public ResponseEntity<List<Address>> findAddressesByProfileIdAndUserId(
			@PathVariable("userId") Integer userId,
			@PathVariable("profileId") Integer profileId){
				return new ResponseEntity <List<Address>>(addressService.findAddressesByprofileIdAndUserId
						(userId,profileId), HttpStatus.OK);
			}	

	@PostMapping
	public ResponseEntity<Address> createAddress(
			@PathVariable("userId") Integer userId,
			@PathVariable("profileId") Integer profileId,
			@RequestBody Address address){
		return new ResponseEntity<Address>(addressService.create(userId,profileId ,address), HttpStatus.CREATED);
	}
	
	@PutMapping("/{addressid}")
	public ResponseEntity<Address> updateAddress(@PathVariable Integer addressid, @RequestBody Address address) {
		return new ResponseEntity<>(addressService.updateAddress(addressid,address),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{addressid}")
	public ResponseEntity<Void> deleteAddress(@PathVariable("addressid") Integer addressid) {
		addressService.deleteAddress(addressid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
