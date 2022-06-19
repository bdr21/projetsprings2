package com.miola.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<ReviewModel> getAll() {
        return reviewRepository.findAll();
    }

    public ReviewModel save(ReviewModel reviewModel) {
        return reviewRepository.save(reviewModel);
    }

    public Float getAvgRatingByEndroitId(int endroitId) {
        return reviewRepository.getAvgRatingByEndroitId(endroitId);
    }
    public Integer getNumberOfReviewsByEndroitId(int endroitId) {
        return reviewRepository.getNumberOfReviewsByEndroitId(endroitId);
    }
}
