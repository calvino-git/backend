package com.example.backend.jwt.controllers;

import com.example.backend.jwt.models.AuthenticationRequest;
import com.example.backend.jwt.models.AuthenticationResponse;
import com.example.backend.jwt.services.ApplicationUserDetailsService;
import com.example.backend.jwt.util.JwtUtil;
import com.example.backend.user.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final ApplicationUserDetailsService applicationUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

//    @PostMapping("/authenticate")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws NoSuchAlgorithmException {
//        try {
//            applicationUserDetailsService.authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
//        } catch (BadCredentialsException e) {
//            throw new BadCredentialsException("Incorrect username or password", e);
//        }
//        final UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());
//        final String jwt = jwtUtil.generateToken(userDetails);
//        return ResponseEntity.ok(new AuthenticationResponse(jwt));
//    }
    @RequestMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws NoSuchAlgorithmException {
        UserEntity userEntity;
        try {
            userEntity = applicationUserDetailsService.authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }
        var userDetails = applicationUserDetailsService.loadUserByUsername(userEntity.getEmail());
        System.out.println(userDetails);
        var jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthenticationResponse(jwt));
    }
}