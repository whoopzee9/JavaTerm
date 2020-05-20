package com.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.course.repository.UserRepository;
import com.course.security.jwt.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/signIn")
    public ResponseEntity signIn(@RequestBody AuthRequest request) {
        try {
            String userName = request.getUserName();
            String token = jwtTokenProvider.createToken(userName,
                    userRepository.findUserByUserName(userName)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found!")).getRoles());

            Map<Object, Object> model = new HashMap<>();
            model.put("userName", userName);
            model.put("token", token);

            return ResponseEntity.ok(model);
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid username or password!");
        }
    }

}
