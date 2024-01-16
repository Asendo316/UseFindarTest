package com.usefindar.app.controllers.api;

import com.usefindar.app.entities.user.User;
import com.usefindar.app.models.request.LoginRequest;
import com.usefindar.app.models.request.SignupRequest;
import com.usefindar.app.models.response.LoginResponse;
import com.usefindar.app.models.response.system.ApiResponse;
import com.usefindar.app.service.authentication.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * UserController
 */
@RestController
@RequestMapping("/api/auth")
@Validated
@CrossOrigin
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<User>> signup(@Valid @RequestBody SignupRequest request) {
    var newUser = authService.createNewUser(request);
    var response = new ApiResponse<User>(HttpStatus.CREATED);
    response.setMessage("Successfully signed up");
    response.setData(newUser);
    return new ResponseEntity<>(response,response.getStatus());
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
    var loginResponseDTO = authService.loginUser(request);
    var response = new ApiResponse<LoginResponse>(HttpStatus.OK);
    response.setMessage("Login Successful");
    response.setData(loginResponseDTO);
    return new ResponseEntity<>(response, response.getStatus());
  }

  @GetMapping("/get-current-user/{token}")
  public ResponseEntity<ApiResponse<User>> getCurrentUser(@PathVariable("token") String token){
    var response = new ApiResponse<User>(HttpStatus.OK);
    var user = authService.getCurrentUser(token);
    response.setMessage("Current user fetched");
    response.setData(user);
    return new ResponseEntity<>(response, response.getStatus());
  }

  @PostMapping("logout")
  public ResponseEntity<ApiResponse<?>> logout(HttpServletRequest request) {
    authService.logout(request);
    var response = new ApiResponse<>(HttpStatus.OK);
    response.setMessage("Logout Successful");
    return new ResponseEntity<>(response, response.getStatus());
  }
}