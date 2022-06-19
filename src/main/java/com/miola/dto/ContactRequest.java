package com.miola.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
//import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter @Setter
public class ContactRequest {
    private String firstName;
    private String lastName;
    private String email;
    @NotBlank
    private String subject;
    @NotBlank
    private String message;
}
