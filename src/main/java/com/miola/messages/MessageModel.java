package com.miola.messages;

import com.miola.users.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class MessageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String subject;
    @NotBlank
    private String message;

//    @OneToMany
//    private UserModel user;
}