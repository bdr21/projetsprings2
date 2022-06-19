package com.miola.endroits;

import com.miola.reviews.ReviewService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
public class EndroitListener {
    private static Log log = LogFactory.getLog(EndroitListener.class);

    private static ReviewService reviewService;

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(EndroitModel _endroit) {
        if (_endroit.getId() == 0) {
            log.info("[ENDROIT AUDIT] About to add a _endroit");
        } else {
            log.info("[ENDROIT AUDIT] About to update/delete _endroit: " + _endroit.getId());
        }
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate(EndroitModel _endroit) {
        log.info("[ENDROIT AUDIT] add/update/delete complete for _endroit: " + _endroit.getId());
    }

    @PostLoad
    private void afterLoad(EndroitModel _endroit) {
        int _endroitId = _endroit.getId();
        Float ratingAvg = reviewService.getAvgRatingByEndroitId(_endroitId);
        Integer numberOfReviews = reviewService.getNumberOfReviewsByEndroitId(_endroitId);
        if (ratingAvg != null || numberOfReviews != null) {
            if (ratingAvg != null) {
                _endroit.setRatingAvg(ratingAvg);
            }
            if (numberOfReviews != null) {
                _endroit.setNumberOfReviews(numberOfReviews);
            }
        } else {
            System.out.println("[ENDROIT AUDIT] No review found for _endroit: " + _endroitId);
        }
    }
}
