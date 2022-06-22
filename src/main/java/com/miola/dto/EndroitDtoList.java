package com.miola.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class EndroitDtoList {
    int id;
    String name;
    String description;
    String image;
    String videoLink;
    float ratingAvg;
    int numberOfReviews;
    private int villeId;
    private String villeName;
    List<ReviewDto> reviews;
}
