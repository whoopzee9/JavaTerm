package com.course.controller;

import com.course.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

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

    @Autowired
    public void setService(AuthService service) {
        this.service = service;
    }

}
