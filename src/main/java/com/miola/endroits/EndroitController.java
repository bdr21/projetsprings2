package com.miola.endroits;


import com.miola.dto.ResponseWithArray;

import com.miola.exceptions.ResourceNotFoundException;
import com.miola.responseMessages.ControllerMessages;
import com.miola.reviews.ReviewModel;
import com.miola.villes.VilleModel;
import com.miola.villes.VilleRepository;
import com.miola.dto.ResponseWithRecordCount;
import com.miola.users.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;
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



    //Afficher tous les endroits ou bien un seul avec son nom
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


    // return all endroits sorted by the specified field (url : http://localhost:8080/endroits/all?sortBy=id)
    @GetMapping(path = "/all/sorting")
    public ResponseEntity<ResponseWithRecordCount> getAllEndroitsWithSort(@RequestParam(name = "sortBy") String sortBy){
        List<EndroitModel> list = endroitService.findEndroitsWithSorting(sortBy);
        ResponseWithRecordCount response = new ResponseWithRecordCount(HttpStatus.OK, ControllerMessages.SUCCESS, list.size(), list);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping(path = "/all/pagination")
    public ResponseEntity<ResponseWithRecordCount> getAllEndroitsWithPagination(
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "pageSize") int pageSize){
        Page<EndroitModel> list = endroitService.findEndroitsWithPagination(offset, pageSize);
        ResponseWithRecordCount response = new ResponseWithRecordCount(HttpStatus.OK, ControllerMessages.SUCCESS, list.getSize(), list);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping(path = "/all/paginationAndSorting")
    public ResponseEntity<ResponseWithRecordCount> getAllEndroitsWithPaginationAndSorting(
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "pageSize") int pageSize,
            @RequestParam(name = "sortBy") String sortBy){
        Page<EndroitModel> list = endroitService.findEndroitsWithPaginationAndSorting(offset, pageSize, sortBy);
        ResponseWithRecordCount response = new ResponseWithRecordCount(HttpStatus.OK, ControllerMessages.SUCCESS, list.getSize(), list);
        return new ResponseEntity<>(response, response.getStatus());
    }




    /*@PostMapping(path = "")
    public ResponseEntity<Object> addEndroit(@Validated @RequestBody EndroitModel endroit) {
        endroitService.addOne(endroit);
        return new ResponseEntity<>(endroit, HttpStatus.CREATED);
    }*/

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
    public ResponseEntity<EndroitModel> updateEndroit(@Validated @PathVariable("id") int id, @RequestBody EndroitModel endroiRequest) {
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


    /*// search endroits by ville
    @GetMapping(path = "/ville/{ville}")
    public ResponseEntity<List<EndroitModel>> getEndroitsByVille(@PathVariable String ville) {
        // get all endroits from DB
        List<EndroitModel> endroitsList = endroitService.getAll();
        // declare the list that will contain the places of the specified city
        List<EndroitModel> le = new ArrayList<>();
        // put all the places which have the specified city in the list
        for (EndroitModel e : endroitsList) {
            if (e.getVille().equals(ville)) le. add(e);
        }
        if(! le.isEmpty())  return new ResponseEntity<>(le, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    // search endroit by name
    @GetMapping(path = "/name/{name}")
    public ResponseEntity<EndroitModel> getEndroitByName(@PathVariable String name) {
        // get all endroits from DB
        List<EndroitModel> endroitsList = endroitService.getAll();
        for (EndroitModel e : endroitsList) {
            if (e.getName().equals(name))
                return new ResponseEntity<>(e, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }*/

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Object> updateEndroit(@Validated @RequestBody EndroitModel endroit){
        Optional<EndroitModel> endroitModel = endroitService.getOneById(endroit.getId());
        if(endroitModel.isPresent()){
            EndroitModel newEndroit = endroitModel.get();
            newEndroit.setName(endroit.getName());
            newEndroit.setVille(endroit.getVille());
            endroitService.update(newEndroit);
            return new ResponseEntity<>(newEndroit, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/delete/{id}")
    public HttpStatus delete(@PathVariable int id){
        Optional<EndroitModel> endroitModel = endroitService.getOneById(id);
        if(endroitModel.isPresent()){
            endroitService.delete(id);
            return HttpStatus.OK;
        } else
            return HttpStatus.BAD_REQUEST;
    }

}
