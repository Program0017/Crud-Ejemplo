package com.globant.training.spring.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.globant.training.spring.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
