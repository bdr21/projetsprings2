package com.miola.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class ResponseWithToken {
    private HttpStatus status;
    private String message;
    private String token;
}
