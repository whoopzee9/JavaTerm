package com.course.controller;

import com.course.entity.User;
import com.course.exception.InvalidPasswordException;
import com.course.service.AuthService;
import com.course.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.course.repository.jpa.UserRepository;
import com.course.security.jwt.JwtTokenProvider;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping(value = "/signIn", consumes = "application/json", produces = "application/json")
    public ResponseEntity signIn(@RequestBody AuthRequest request) {
        try {
            return ResponseEntity.ok(service.signIn(request));
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid username or password!");
        }
    }

    @PostMapping(value = "/signUp", consumes = "application/json", produces = "application/json")
    public ResponseEntity signUp(@RequestBody AuthRequest request) {
        try {
            Map<Object, Object> model = service.signUp(request);

            if (model == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok(model);
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid username or password!");
        }
    }

}
