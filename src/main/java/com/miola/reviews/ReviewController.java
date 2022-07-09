package com.miola.reviews;

import com.miola.dto.ResponseWithArray;
import com.miola.dto.ReviewDto;
import com.miola.endroits.EndroitService;
import com.miola.exceptions.ResourceNotFoundException;
import com.miola.responseMessages.ControllerMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private EndroitService endroitService;

    @Autowired
    private ReviewRepository reviewRepository;

    //Afficher tous les reviews
    @GetMapping(path = "")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ResponseWithArray> getAll() {
        List<ReviewModel> list = reviewService.getAll();
        List<ReviewDto> list1 = new ArrayList<>();
        for (ReviewModel r:list){
            ReviewDto reviewDto = new ReviewDto(r.getId(),r.getContenu(),r.getRating(),
                    r.getUser().getId(),r.getUser().getFirstName()+" "+r.getUser().getLastName());
            list1.add(reviewDto);
        }

        ResponseWithArray response = new ResponseWithArray(HttpStatus.OK, ControllerMessages.SUCCESS, list1);
        return new ResponseEntity<ResponseWithArray>(response, response.getStatus());
    }

//    @PostMapping("")
//    public ResponseEntity<ReviewModel> addReview(@RequestBody ReviewModel review) {
////        float _rating = review.getRating();
////        int _idEndroit = review.getEndroit().getId();
////
////        Optional<EndroitModel> _endroitOp = endroitService.getOneById(_idEndroit);
////        EndroitModel _endroit = _endroitOp.get();
//
//        ReviewModel _review = reviewService.save(review);
//        return new ResponseEntity<>(_review, HttpStatus.CREATED);
//    }

    // search reviews by id
    @GetMapping(path="/{id}")
    public ResponseEntity<ReviewModel> getReviewById(@PathVariable("id") int id) {
        ReviewModel review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Review with id = " + id));
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    //Modifier un review
    @PostMapping(path="/{id}")
    public ResponseEntity<ReviewModel> updateReview(@PathVariable("id") int id, @RequestBody ReviewModel reviewRequest) {
        ReviewModel review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReviewId " + id + "not found"));
        String contenu = reviewRequest.getContenu();
        float rating = reviewRequest.getRating();
        if (contenu != null) {
            review.setContenu(contenu);
        }
        if (rating != 0) {
            review.setRating(rating);
        }
        return new ResponseEntity<>(reviewRepository.save(review), HttpStatus.OK);
    }

    //Supprimer un review
    @DeleteMapping(path="/{id}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable("id") int id) {
        reviewRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
