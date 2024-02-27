package com.globant.training.spring.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.globant.training.spring.entities.Role;
import com.globant.training.spring.services.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/roles")
public class RoleControllers {
    
    private final RoleService roleService;
    
    @Autowired
    public RoleControllers(RoleService roleService){
        this.roleService = roleService;
    }
    
    @Operation(summary = "Get all roles", description = "Retrieve a list of roles")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class)))
    @GetMapping
    public ResponseEntity<List<Role>> getRoles(){
        return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
    }
    
    @Operation(summary = "Create a role", description = "Add a new role to the system")
    @ApiResponse(responseCode = "201", description = "Role created")
    @PostMapping
    public ResponseEntity<Role> createRole(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Role to be added", required = true, content = @Content(schema = @Schema(implementation = Role.class))) @RequestBody Role role){
        return new ResponseEntity<>(roleService.createRole(role), HttpStatus.CREATED);
    }
    
    @Operation(summary = "Update a role", description = "Modify an existing role by ID")
    @ApiResponse(responseCode = "200", description = "Role updated")
    @PutMapping("/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable("roleId") Integer roleId, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Role with updated data", required = true, content = @Content(schema = @Schema(implementation = Role.class))) @RequestBody Role role){
        return new ResponseEntity<>(roleService.updateRole(roleId, role), HttpStatus.OK);
    }
    
    @Operation(summary = "Delete a role", description = "Remove a role from the system by ID")
    @ApiResponse(responseCode = "204", description = "Role deleted")
    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable("roleId") Integer roleId){
        roleService.deleteRole(roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}