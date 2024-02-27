package com.globant.training.spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.globant.training.spring.entities.Address;
import com.globant.training.spring.entities.Profile;
import com.globant.training.spring.entities.User;

@Repository
public interface AddressRepository  extends CrudRepository<Address, Integer>{
	
	@Query("SELECT a FROM Address a WHERE a.profile.user.id =:userId AND a.profile.id =:profileId")
	List<Address> findByProfileId(Integer profileId, Integer userId);
	
	@Query("SELECT p FROM Address p WHERE p.profile = :profile")
    Optional<Address> findByProfile(@Param("profile") Profile profile);
}
