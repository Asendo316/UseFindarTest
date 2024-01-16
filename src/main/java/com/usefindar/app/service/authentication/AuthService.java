package com.usefindar.app.service.authentication;

import com.usefindar.app.entities.user.User;
import com.usefindar.app.models.request.LoginRequest;
import com.usefindar.app.models.request.SignupRequest;
import com.usefindar.app.models.response.LoginResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * AuthService
 */
public interface AuthService {

  User createNewUser(SignupRequest user);

  void logout(HttpServletRequest request);

  LoginResponse loginUser(LoginRequest user);

  User getCurrentUser(String userId);

}
