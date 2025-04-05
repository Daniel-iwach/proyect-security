package com.securityapp.spring_security.presentation.controller;

import com.securityapp.spring_security.presentation.dto.UserDTO;
import com.securityapp.spring_security.service.interfaces.IRolService;
import com.securityapp.spring_security.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "User", description = "Endpoints for user registration, profile retrieval, and updating")
public class UserController {

    @Autowired
    private IUserService userService;

    @Operation(
            summary = "Get user profile by username",
            description = "This endpoint allows retrieving the profile of a user based on their username. Only the user or an administrator can access this endpoint."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied: User must be the owner of the profile or an ADMIN"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/get/{username}")
    @PreAuthorize("#username == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable String username) {
        return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
    }

    @Operation(
            summary = "Update user profile",
            description = "This endpoint allows updating the user's profile. Only the user or an administrator can update the profile."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile updated successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied: User must be the owner of the profile or an ADMIN"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/update/{username}")
    @PreAuthorize("#username == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String username, @Valid @RequestBody UserDTO newUser) {
        return new ResponseEntity<>(userService.update(username, newUser), HttpStatus.OK);
    }
}



