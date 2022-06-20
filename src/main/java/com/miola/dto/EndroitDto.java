package com.miola.dto;

import com.miola.reviews.ReviewModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class EndroitDto {
    int id;
    String name;
    String description;
    String image;
    String videoLink;
    float ratingAvg;
    int numberOfReviews;
    private int villeId;
    private String villeName;
    List<ReviewModel> reviews;
}
