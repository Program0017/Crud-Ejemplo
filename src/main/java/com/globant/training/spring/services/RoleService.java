package com.globant.training.spring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.globant.training.spring.entities.Role;
import com.globant.training.spring.repositories.RoleRepository;

@Service
public class RoleService {
	
	private RoleRepository roleRepository;

	@Autowired
	public RoleService(RoleRepository repository) {
		this.roleRepository = repository;
	}

	public List<Role> getRoles(){
		return roleRepository.findAll();
	}

	public Role createRole(Role role) {
		return roleRepository.save(role);	
	}
	
	public Role updateRole(Integer roleId, Role role){
		Optional<Role> result = roleRepository.findById(roleId);
		if (result.isPresent()) {
	        return roleRepository.save(role);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role %s doesn't exist", roleId));
	    }
	}

	public void deleteRole(Integer roleId) {
		Optional<Role> result = roleRepository.findById(roleId);
		
		if(result.isPresent()) {
			 roleRepository.delete(result.get());
		}else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role %s doesn't exist", roleId));

		}
	}
}
