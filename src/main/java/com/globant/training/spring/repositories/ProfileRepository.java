package com.globant.training.spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.globant.training.spring.entities.Profile;
import com.globant.training.spring.entities.User;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Integer> {

   
    @Query("SELECT p FROM Profile p WHERE p.user.id =:userId AND p.id =:profileId")
    Optional<Profile> findByUserIdAndProfileId(Integer userId, Integer profileId);

    @Query("SELECT p FROM Profile p WHERE p.user = :user")
    Optional<Profile> findByUser(@Param("user") User user);

}
