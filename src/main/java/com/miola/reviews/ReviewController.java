package com.miola.reviews;

import com.miola.dto.ResponseWithArray;
import com.miola.endroits.EndroitModel;
import com.miola.exceptions.ResourceNotFoundException;
import com.miola.messages.ControllerMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;


    @GetMapping(path = "")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ResponseWithArray> getAll() {
        List<ReviewModel> list = reviewService.getAll();
        ResponseWithArray response = new ResponseWithArray(HttpStatus.OK, ControllerMessages.SUCCESS, list);
        return new ResponseEntity<ResponseWithArray>(response, response.getStatus());
    }

    @PostMapping("")
    public ResponseEntity<ReviewModel> addReview(@RequestBody ReviewModel review) {
        ReviewModel _review = reviewService.save(new ReviewModel(review.getId(),review.getContenu(),review.getEndroits(),review.getUsers()));
        return new ResponseEntity<>(_review, HttpStatus.CREATED);
    }

    // search reviews by id
    @GetMapping(path="/{id}")
    public ResponseEntity<ReviewModel> getReviewById(@PathVariable("id") int id) {
        ReviewModel review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Review with id = " + id));
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @PutMapping(path="/{id}")
    public ResponseEntity<ReviewModel> updateReview(@PathVariable("id") int id, @RequestBody ReviewModel reviewRequest) {
        ReviewModel review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReviewId " + id + "not found"));
        review.setContenu(reviewRequest.getContenu());
        return new ResponseEntity<>(reviewRepository.save(review), HttpStatus.OK);
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable("id") int id) {
        reviewRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
