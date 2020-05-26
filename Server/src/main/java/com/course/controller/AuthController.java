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
import com.course.repository.jpa.UserRepository;
import com.course.security.jwt.JwtTokenProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/signIn", consumes = "application/json", produces = "application/json")
    public ResponseEntity signIn(@RequestBody AuthRequest request) {
        try {
            String userName = request.getUserName();
            String password = request.getPassword();
            List<String> roles = userRepository.findUserByUserName(userName)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found!")).getRoles();
            String token = jwtTokenProvider.createToken(userName, password, roles);

            Map<Object, Object> model = new HashMap<>();
            model.put("userName", userName);
            model.put("token", token);
            model.put("roles", roles);

            return ResponseEntity.ok(model);
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid username or password!");
        }
    }

}
