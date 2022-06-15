package com.miola.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@Getter @Setter
public class UserDetailsWithoutPwd {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
}
