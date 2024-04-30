package com.bestapp.SpringSecurityWithJWT.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the resources
 */
@RestController
@RequestMapping("/resources")
@Tag(name = "Resources", description = "API for resource management")
public class ResourceController {

    /**
     * Getting user resource.
     * @return the response with the found user resource in JSON format and the HTTP 200 status code (Ok).<br>
     * If the user resource not found the HTTP status code 404 (Not found).
     */
    @Operation(
            summary = "Find user resource",
            description = "Search for user resource",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "User resource successfully found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)
                            )),
                    @ApiResponse(responseCode = "200", description = "User resource successfully found", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> getUserResource(Authentication authentication) {
        String userString = "User resource.";
        return ResponseEntity.ok(userString);
    }

    /**
     * Getting admin resource.
     * @return the response with the found admin resource in JSON format and the HTTP 200 status code (Ok).<br>
     * If the admin resource not found the HTTP status code 404 (Not found).
     */
    @Operation(
            summary = "Find admin resource",
            description = "Search for admin resource",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Admin resource successfully found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = String.class)
                            )),
                    @ApiResponse(responseCode = "200", description = "Admin resource successfully found", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getAdminResource(Authentication authentication) {
        String adminString = "Admin resource.";
        return ResponseEntity.ok(adminString);
    }
}
