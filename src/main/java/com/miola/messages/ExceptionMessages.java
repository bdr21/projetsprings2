package com.miola.messages;

import lombok.Getter;

@Getter
public class ExceptionMessages {
    public static final String PASSWORDS_NOT_MATCHING_MESSAGE = "Passwords do not match";
    public static final String USER_ALREADY_EXISTS_MESSAGE = "User already exists";
    public static final String USER_DOESNT_EXIST_MESSAGE = "User does not exist";
    public static final String LOG_IN_FAIL_MESSAGE = "LOG_IN_FAIL_MESSAGE";
    public static final String NOT_LOGGED_IN_OR_INVALID_AUTH_HEADER = "You need to login before accessing this resource or check your http authorization";
    public static final String CORRUPT_TOKEN = "Couldn't extract credentials from token. Please try again";
    public static final String INVALID_TOKEN = "Invalid token";
    public static final String GENERAL_AUTH_EXCEPTION = "There was an authorization error. Please try again";
}
