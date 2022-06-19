package com.miola.reviews;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewModel, Integer> {

    @Query("SELECT avg(r.rating) FROM ReviewModel r WHERE r.endroit.id = ?1")
    Float getAvgRatingByEndroitId(int endroitId);

    @Query("SELECT count(r.id) FROM ReviewModel r WHERE r.endroit.id = ?1")
    Integer getNumberOfReviewsByEndroitId(int endroitId);

}
