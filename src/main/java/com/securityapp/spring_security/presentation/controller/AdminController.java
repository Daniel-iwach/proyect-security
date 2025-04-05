package com.securityapp.spring_security.presentation.controller;

import com.securityapp.spring_security.presentation.dto.UserDTO;
import com.securityapp.spring_security.service.interfaces.IAdminService;
import com.securityapp.spring_security.service.interfaces.IRolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin", description = "Endpoints for user management by administrators")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IRolService rolService;

    @Operation(
            summary = "Get all users",
            description = "This endpoint allows administrators to retrieve a list of all registered users."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users successfully retrieved"),
            @ApiResponse(responseCode = "403", description = "Access denied: ADMIN role required")
    })
    @GetMapping("/showAll")
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        return new ResponseEntity<>(adminService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a user by username",
            description = "This endpoint allows administrators to delete a specific user by their username."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully deleted"),
            @ApiResponse(responseCode = "403", description = "Access denied: ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        return new ResponseEntity<>(adminService.deleteByUsername(username), HttpStatus.OK);
    }
}


