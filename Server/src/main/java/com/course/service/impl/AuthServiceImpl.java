package com.course.service.impl;

import com.course.controller.AuthRequest;
import com.course.entity.User;
import com.course.repository.jpa.UserRepository;
import com.course.security.jwt.JwtTokenProvider;
import com.course.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<Object, Object> signIn(AuthRequest authRequest) {
        String userName = authRequest.getUserName();
        String password = authRequest.getPassword();
        List<String> roles = userRepository.findUserByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!")).getRoles();

        String token = jwtTokenProvider.createToken(userName, password, roles);

        Map<Object, Object> model = new HashMap<>();
        model.put("userName", userName);
        model.put("token", token);
        model.put("roles", roles);

        return model;
    }

    @Override
    public Map<Object, Object> signUp(AuthRequest authRequest) {
        String userName = authRequest.getUserName();
        String password = authRequest.getPassword();
        if (userRepository.findUserByUserName(userName).isPresent()) {
            return null;
        }

        List<String> roles = Collections.singletonList("ROLE_USER");
        User user = new User(userName, passwordEncoder.encode(password), roles);
        userRepository.save(user);

        String token = jwtTokenProvider.createToken(userName, password, roles);

        Map<Object, Object> model = new HashMap<>();
        model.put("userName", userName);
        model.put("token", token);
        model.put("roles", roles);

        return model;
    }
}
