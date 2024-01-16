package com.usefindar.app.configuration;

import com.usefindar.app.exceptions.CustomAccessDeniedException;
import com.usefindar.app.exceptions.CustomException;
import com.usefindar.app.exceptions.ResourceNotFoundException;
import com.usefindar.app.models.response.system.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * RequestExceptionHandler
 */
@ControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException ce) {
        var ar = new ApiResponse<>(ce.getStatus());
        ar.setError(ce.getMessage());
        return buildResponseEntity(ar);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException iae) {
        var ar = new ApiResponse<>(HttpStatus.BAD_REQUEST);
        ar.setError(iae.getLocalizedMessage());
        return buildResponseEntity(ar);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException hmre, HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {
        var ar = new ApiResponse<>(HttpStatus.BAD_REQUEST);
        ar.setError("Validation Error: " + hmre.getMostSpecificCause().getLocalizedMessage());
        return buildResponseEntity(ar);
    }

    @Override
    public ResponseEntity<Object> handleBindException(BindException be, HttpHeaders headers, HttpStatus status,
                                                      WebRequest request) {
        var ar = new ApiResponse<>(HttpStatus.BAD_REQUEST);
        ar.addValidationErrors(be.getFieldErrors());
        ar.setError("Validation Error");
        return buildResponseEntity(ar);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException e) {
        var ar = new ApiResponse<>(HttpStatus.UNPROCESSABLE_ENTITY);
        ar.setError(e.getMessage());
        return buildResponseEntity(ar);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException mx, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        var ar = new ApiResponse<>(HttpStatus.BAD_REQUEST);
        ar.addValidationError(mx.getBindingResult().getAllErrors());
        ar.setError("Validation Error");
        return buildResponseEntity(ar);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException rnfe) {
        var ar = new ApiResponse<>(HttpStatus.NOT_FOUND);
        ar.setError(rnfe.getMessage());
        return buildResponseEntity(ar);
    }

    @ExceptionHandler(CustomAccessDeniedException.class)
    public ResponseEntity<Object> handleCustomAccessDeniedException(CustomAccessDeniedException e) {
        var ar = new ApiResponse<>(HttpStatus.FORBIDDEN);
        ar.setError(e.getMessage());
        return buildResponseEntity(ar);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiResponse<?> apiResponse) {
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }
}