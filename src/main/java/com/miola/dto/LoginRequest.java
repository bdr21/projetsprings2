package com.miola.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    @Pattern(regexp = "^.+@.+\\..+$" , message = "Email is not valid")
    private String email;
    private String password;

}
