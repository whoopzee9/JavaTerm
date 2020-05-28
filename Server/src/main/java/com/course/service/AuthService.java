package com.course.service;

import com.course.controller.AuthRequest;

import java.util.Map;

public interface AuthService {
    Map<Object, Object> signIn(AuthRequest authRequest);
    Map<Object, Object> signUp(AuthRequest authRequest);
}
