package com.miola.exceptions;

import com.miola.dto.BasicResponse;
import com.miola.messages.ExceptionMessages;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExHandler {

    private ResponseEntity<BasicResponse> responseHandler(HttpStatus status, String message) {
        BasicResponse response = new BasicResponse(status, message);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BasicResponse> handle(UserAlreadyExistsException ex) {
        BasicResponse response = new BasicResponse(HttpStatus.BAD_REQUEST, ExceptionMessages.USER_ALREADY_EXISTS_MESSAGE);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(PasswordsNotMatchingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BasicResponse> handle(PasswordsNotMatchingException ex) {
        BasicResponse response = new BasicResponse(HttpStatus.BAD_REQUEST, ExceptionMessages.PASSWORDS_NOT_MATCHING_MESSAGE);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(UserDoesntExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BasicResponse> handle(UserDoesntExistException ex) {
        BasicResponse response = new BasicResponse(HttpStatus.BAD_REQUEST, ExceptionMessages.USER_DOESNT_EXIST_MESSAGE);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(LoginFailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BasicResponse> handle(LoginFailException ex) {
        BasicResponse response = new BasicResponse(HttpStatus.BAD_REQUEST, ExceptionMessages.LOG_IN_FAIL_MESSAGE);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(GeneralAuthentificationException.class)
    public ResponseEntity<BasicResponse> handle(GeneralAuthentificationException ex) {
        return responseHandler(HttpStatus.UNAUTHORIZED, ExceptionMessages.GENERAL_AUTH_EXCEPTION);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<BasicResponse> handle(InvalidTokenException ex) {
        return responseHandler(HttpStatus.UNAUTHORIZED, ExceptionMessages.INVALID_TOKEN);
    }

    @ExceptionHandler(CorruptTokenException.class)
    public ResponseEntity<BasicResponse> handle(CorruptTokenException ex) {
        return responseHandler(HttpStatus.UNAUTHORIZED, ExceptionMessages.CORRUPT_TOKEN);
    }

    @ExceptionHandler(NotLoggedInOrInvalidAuthorizationHeaderException.class)
    public ResponseEntity<BasicResponse> handle(NotLoggedInOrInvalidAuthorizationHeaderException ex) {
        return responseHandler(HttpStatus.UNAUTHORIZED, ExceptionMessages.NOT_LOGGED_IN_OR_INVALID_AUTH_HEADER);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<BasicResponse> handle(JwtException ex) {
        return responseHandler(HttpStatus.BAD_REQUEST, ex.getMessage());
    }



}
