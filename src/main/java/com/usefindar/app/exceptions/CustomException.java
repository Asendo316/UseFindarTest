package com.usefindar.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * CustomException
 */
@Data
@AllArgsConstructor
public class CustomException extends RuntimeException {
  private final String message;
  private final HttpStatus status;
}
