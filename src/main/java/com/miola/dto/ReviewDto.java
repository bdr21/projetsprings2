package com.miola.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ReviewDto {
    int id;
    String contenu;
    float rating;
    int idUser;
    String nameUser;

}
