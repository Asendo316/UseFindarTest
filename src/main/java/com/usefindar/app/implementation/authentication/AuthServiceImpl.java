package com.usefindar.app.implementation.authentication;

import com.usefindar.app.entities.authentication.TokenBlacklist;
import com.usefindar.app.entities.user.User;
import com.usefindar.app.exceptions.CustomException;
import com.usefindar.app.models.request.LoginRequest;
import com.usefindar.app.models.request.SignupRequest;
import com.usefindar.app.models.response.LoginResponse;
import com.usefindar.app.models.response.ValidationObject;
import com.usefindar.app.repository.authentication.TokenBlacklistRepository;
import com.usefindar.app.repository.users.UserRepository;
import com.usefindar.app.security.JwtTokenProvider;
import com.usefindar.app.service.authentication.AuthService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final TokenBlacklistRepository tokenBlacklistRepository;


    /***
     * Create new account
     * @param request
     * @return
     */
    @Override
    public User createNewUser(SignupRequest request) {
        var validation = validateSignupDetails(request);
        try{
            var user = new User();
            if (Boolean.TRUE.equals(validation.getIsValid())) {
                user.setEmail(request.getEmail());
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }else{
                throw new CustomException(validation.getValidationMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            userRepository.save(user);
            user.setPassword("**********");
            return user;
        }catch (Exception e){
            throw new CustomException(e .getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /***
     * Resolve User from Jwt and end session
     * @param request
     */
    @Override
    public void logout(HttpServletRequest request) {
        var token = jwtTokenProvider.resolveToken(request);
        var email = jwtTokenProvider.getUserId(token);
        var tokenBlacklist = new TokenBlacklist();
        tokenBlacklist.setToken(token);
        tokenBlacklist.setEmail(email);
        tokenBlacklistRepository.save(tokenBlacklist);
    }

    /***
     * Generate new session
     * @param request
     * @return
     */
    @Override
    public LoginResponse loginUser(LoginRequest request) {
        try {
            logger.info("Authenticating user {}...", request.getEmail());
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            var loggedInUser = userRepository.findUserByEmail(request.getEmail())
                    .orElseThrow(() ->
                            new CustomException("Invalid email/password supplied...", HttpStatus.UNPROCESSABLE_ENTITY));

            var token = jwtTokenProvider.createToken(loggedInUser.getEmail());

            var response = new LoginResponse();
            response.setToken(token);
            response.setId(loggedInUser.getId());
            response.setEmail(loggedInUser.getEmail());
            response.setFirstName(loggedInUser.getFirstName());
            response.setLastName(loggedInUser.getLastName());

            logger.info("Responding with {}", response);
            return response;
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /***
     * Resolve user from session token
     * @param token
     * @return
     */
    @Override
    public User getCurrentUser(String token) {
        return this.getAgentByUserEmail(jwtTokenProvider.getUserId(token));
    }

    /***
     *
     * @param email
     * @return
     */
    private User getAgentByUserEmail(String email) {
        var user = userRepository.findUserByEmail(email)
                .orElseThrow(() ->
                        new CustomException("Invalid email/password supplied...", HttpStatus.UNPROCESSABLE_ENTITY));

        user.setPassword("******************");
        return user;
    }

    /**
     * Validating Request Body
     *
     * @param request
     * @return
     */
    private ValidationObject validateSignupDetails(SignupRequest request) {
        var validationObject = new ValidationObject(false, "Failed");

        if (isNullOrEmpty(request.getFirstName())) {
            validationObject.setValidationMessage("First Name cannot be empty");
        } else if (isNullOrEmpty(request.getLastName())) {
            validationObject.setValidationMessage("Last Name cannot be empty");
        } else if (isNullOrEmpty(request.getEmail())) {
            validationObject.setValidationMessage("Email cannot be empty");
        } else if (isNullOrEmpty(request.getPassword())) {
            validationObject.setValidationMessage("Password cannot be empty");
        } else {
            validationObject.setIsValid(true);
            validationObject.setValidationMessage("Validation Successful");
        }

        return validationObject;
    }

    private boolean isNullOrEmpty(String value) {
        return Objects.isNull(value) || value.trim().isEmpty();
    }
}
