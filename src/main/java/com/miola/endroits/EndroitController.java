package com.miola.endroits;


import com.miola.dto.ResponseWithArray;

import com.miola.exceptions.ResourceNotFoundException;
import com.miola.responseMessages.ControllerMessages;
import com.miola.reviews.ReviewModel;
import com.miola.villes.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/endroits")
public class EndroitController {

    @Autowired
    private EndroitService endroitService;

    @Autowired
    private EndroitRepository endroitRepository;

    @Autowired
    private VilleRepository villeRepository;


    @GetMapping(path = "")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> getAll(@RequestParam(value="name" , required = false) String name) {
        if (name != null) {
            Optional<EndroitModel> endroit = endroitRepository.findByName(name);
            return new ResponseEntity<>(endroit.get(), HttpStatus.OK);
        }
        List<EndroitModel> list = endroitService.getAll();
        ResponseWithArray response = new ResponseWithArray(HttpStatus.OK, ControllerMessages.SUCCESS, list);
        return new ResponseEntity<ResponseWithArray>(response, response.getStatus());
    }


    @PostMapping("")
    public ResponseEntity<EndroitModel> addEndroit(@RequestBody EndroitModel endroit) {
        endroit.getVille().getVille_name();
        EndroitModel _endroit = endroitService.save(new EndroitModel(endroit.getId(), endroit.getName(),endroit.getVille(), endroit.getReviews()));
        return new ResponseEntity<>(_endroit, HttpStatus.CREATED);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<EndroitModel> getEndroitById(@PathVariable("id") int id) {
        EndroitModel endroit = endroitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Endroit with id = " + id));
        return new ResponseEntity<>(endroit, HttpStatus.OK);
    }


    //Get All Reviews of an Endroit
    @GetMapping(path = "/{id}/reviews")
    public ResponseEntity<List<ReviewModel>> getReviewsOfEndroit(@PathVariable("id") int id){
        EndroitModel endroit = endroitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Endroit with id = " + id));
        return new ResponseEntity<>(endroit.getReviews(), HttpStatus.OK);
    }

    @PutMapping(path="/{id}")
    public ResponseEntity<EndroitModel> updateEndroit(@PathVariable("id") int id, @RequestBody EndroitModel endroiRequest) {
        EndroitModel endroit = endroitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EndroitId " + id + "not found"));
        endroit.setName(endroiRequest.getName());
        endroit.setVille(endroiRequest.getVille());
        return new ResponseEntity<>(endroitRepository.save(endroit), HttpStatus.OK);
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<HttpStatus> deleteEndroit(@PathVariable("id") int id) {
        endroitRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*@GetMapping("/{name}")
    public ResponseEntity<List<EndroitModel>> getEndroitByName(@PathVariable("name") String name) {

        List<EndroitModel> endroits = endroitRepository.findByName(name);
        if (endroits.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(endroits, HttpStatus.OK);
    }*/











}
