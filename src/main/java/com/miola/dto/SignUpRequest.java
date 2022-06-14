package com.miola.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank
    @Pattern(regexp = "^.+@.+\\..+$" , message = "Email is not valid")
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Size(min = 6, max = 12, message = "Invalid length for password")
    private String password;

    @NotBlank
    @Size(min = 6, max = 12, message = "Invalid length for password")
    private String confirmPassword;

    @NotBlank
    private String address;
}
