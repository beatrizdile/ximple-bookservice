package org.example.springbootbookservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.springbootbookservice.repository.UserRepository;
import org.example.springbootbookservice.model.LoginResponseDTO;
import org.example.springbootbookservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.springbootbookservice.model.AuthenticationDTO;
import org.example.springbootbookservice.model.RegisterDTO;
import org.example.springbootbookservice.service.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository repository;

    @PostMapping("/register")
    @Operation(
            description = "Register a new USER/ADMIN",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid Credentials",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500"
                    )
            }
    )
    public ResponseEntity register(@RequestBody RegisterDTO data) {
        if (repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());

        repository.save(newUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @Operation(
            description = "Authenticate with registered USER/ADMIN and get a token",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid Credentials",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500"
                    )
            }
    )
    public ResponseEntity login(@RequestBody AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
