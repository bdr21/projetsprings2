package com.miola.endroits;


import com.miola.dto.ResponseWithArray;
import com.miola.exceptions.ResourceNotFoundException;
import com.miola.messages.ControllerMessages;
import com.miola.villes.VilleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @GetMapping(path = "")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ResponseWithArray> getAll() {
        List<EndroitModel> list = endroitService.getAll();
        ResponseWithArray response = new ResponseWithArray(HttpStatus.OK, ControllerMessages.SUCCESS, list);
        return new ResponseEntity<ResponseWithArray>(response, response.getStatus());
    }


    @PostMapping("")
    public ResponseEntity<EndroitModel> addEndroit(@RequestBody EndroitModel endroit) {
        EndroitModel _endroit = endroitService.save(new EndroitModel(endroit.getId(), endroit.getName(),endroit.getVille()));
        return new ResponseEntity<>(_endroit, HttpStatus.CREATED);
    }

    // search endroits by id
    @GetMapping(path="/{id}")
    public ResponseEntity<EndroitModel> getEndroitById(@PathVariable("id") int id) {
        EndroitModel endroit = endroitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Endroit with id = " + id));
        return new ResponseEntity<>(endroit, HttpStatus.OK);
    }


    @DeleteMapping(path="/{id}")
    public ResponseEntity<HttpStatus> deleteEndroit(@PathVariable("id") int id) {
        endroitRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
