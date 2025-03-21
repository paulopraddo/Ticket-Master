package com.ticketmaster.api.app.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketmaster.api.app.auth.dto.AuthenticationDTO;
import com.ticketmaster.api.app.auth.dto.LoginResponseDTO;
import com.ticketmaster.api.app.auth.dto.RegisterDTO;
import com.ticketmaster.api.app.auth.service.TokenService;
import com.ticketmaster.api.domain.auth.model.User;
import com.ticketmaster.api.domain.auth.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated AuthenticationDTO authenticationDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok().body(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Validated RegisterDTO registerDTO) {
        if(this.userRepository.findByUsername(registerDTO.username()) != null) return ResponseEntity.badRequest().build();
        
        String ecnryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = new User(registerDTO.username(), registerDTO.email(), ecnryptedPassword, registerDTO.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().body(newUser);
    }
}
