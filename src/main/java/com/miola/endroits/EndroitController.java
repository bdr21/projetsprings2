package com.miola.endroits;


import com.miola.dto.EndroitDto;
import com.miola.dto.ResponseWithArray;

import com.miola.dto.UserDetailsWithoutPwd;
import com.miola.exceptions.ResourceNotFoundException;
import com.miola.responseMessages.ControllerMessages;
import com.miola.responseMessages.UtilMessages;
import com.miola.reviews.ReviewModel;
import com.miola.reviews.ReviewService;
import com.miola.users.UserController;
import com.miola.villes.VilleModel;
import com.miola.villes.VilleRepository;
import com.miola.dto.ResponseWithRecordCount;
import com.miola.users.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.LinkedList;
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
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserController userController;



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
        List<EndroitDto> endroitDtoList = new LinkedList<>();
        for (EndroitModel endroit: list) {
            endroitDtoList.add(new EndroitDto(
                    endroit.getId(),
                    endroit.getName(),
                    endroit.getDescription(),
                    endroit.getImage(),
                    endroit.getVideoLink(),
                    endroit.getRatingAvg(), endroit.getNumberOfReviews(),
                    endroit.getVille().getId(),
                    endroit.getVille().getVillename(),
                    endroit.getReviews()
            ));
        }
        ResponseWithRecordCount response = new ResponseWithRecordCount(HttpStatus.OK, ControllerMessages.SUCCESS, endroitDtoList.size(), endroitDtoList);
        return new ResponseEntity<>(response, response.getStatus());
    }

    //pagination
    @GetMapping(path = "/all/pagination")
    public ResponseEntity<ResponseWithRecordCount> getAllEndroitsWithPagination(
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "pageSize") int pageSize){
        Page<EndroitModel> list = endroitService.findEndroitsWithPagination(offset, pageSize);
        ResponseWithRecordCount response = new ResponseWithRecordCount(HttpStatus.OK, ControllerMessages.SUCCESS, list.getSize(), list);
        return new ResponseEntity<>(response, response.getStatus());
    }

    //pagination and sorting
    @GetMapping(path = "/all/paginationAndSorting")
    public ResponseEntity<ResponseWithRecordCount> getAllEndroitsWithPaginationAndSorting(
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "pageSize") int pageSize,
            @RequestParam(name = "sortBy") String sortBy){
        Page<EndroitModel> list = endroitService.findEndroitsWithPaginationAndSorting(offset, pageSize, sortBy);
        List<EndroitDto> endroitDtoList = new LinkedList<>();
        for (EndroitModel endroit: list) {
            endroitDtoList.add(new EndroitDto(
                    endroit.getId(),
                    endroit.getName(),
                    endroit.getDescription(),
                    endroit.getImage(),
                    endroit.getVideoLink(),
                    endroit.getRatingAvg(), endroit.getNumberOfReviews(),
                    endroit.getVille().getId(),
                    endroit.getVille().getVillename(),
                    endroit.getReviews()
            ));
        }
        ResponseWithRecordCount response = new ResponseWithRecordCount(HttpStatus.OK, ControllerMessages.SUCCESS, endroitDtoList.size(), endroitDtoList);
        return new ResponseEntity<>(response, response.getStatus());
    }

    //afficher un endroit à partir de son id
    @GetMapping(path="/{id}")
    public ResponseEntity<EndroitDto> getEndroitById(@PathVariable("id") int id) {
        EndroitModel endroit = endroitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Endroit with id = " + id));

        EndroitDto endroitDto = new EndroitDto(endroit.getId(), endroit.getName(), endroit.getDescription(),
                endroit.getImage(), endroit.getVideoLink(),
                endroit.getRatingAvg(), endroit.getNumberOfReviews(),
                endroit.getVille().getId(),
                endroit.getVille().getVillename(),endroit.getReviews());

        return new ResponseEntity<>(endroitDto, HttpStatus.OK);
    }


    //Get All Reviews of an Endroit
    @GetMapping(path = "/{id}/reviews")
    public ResponseEntity<List<ReviewModel>> getReviewsOfEndroit(@PathVariable("id") int id){
        EndroitModel endroit = endroitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Endroit with id = " + id));
        return new ResponseEntity<>(endroit.getReviews(), HttpStatus.OK);
    }

    //Modifier un endroit
    @PostMapping(path="/{id}")
    public ResponseEntity<EndroitModel> updateEndroit(@Validated @PathVariable("id") int id, @RequestBody EndroitModel endroiRequest) {
        EndroitModel endroit = endroitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EndroitId " + id + "not found"));
        endroit.setName(endroiRequest.getName());
        endroit.setDescription(endroiRequest.getDescription());
        endroit.setImage(endroiRequest.getImage());
        return new ResponseEntity<>(endroitRepository.save(endroit), HttpStatus.OK);
    }

    //Supprimer un endroit
    @DeleteMapping(path="/{id}")
    public ResponseEntity<HttpStatus> deleteEndroit(@PathVariable("id") int id) {
        endroitRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Ajouter un review à un endroit
    @PostMapping(path = "/{id}/reviews")
    public ResponseEntity<ReviewModel> addReviewToEndroit(@Validated @PathVariable("id") int id,@RequestBody ReviewModel review){
        EndroitModel endroit = endroitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Endroit with id = " + id));
        Optional<UserModel> user = userController.currentUser();
        ReviewModel _review = reviewService.save(new ReviewModel(review.getId(), review.getContenu(), review.getRating() , endroit,user.get()));
        return new ResponseEntity<>(_review, HttpStatus.CREATED);

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
    }

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
    }*/



}
