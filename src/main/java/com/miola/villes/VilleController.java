package com.miola.villes;

import com.miola.dto.ResponseWithArray;
import com.miola.endroits.EndroitModel;
import com.miola.endroits.EndroitService;
import com.miola.exceptions.ResourceNotFoundException;
import com.miola.responseMessages.ControllerMessages;
import com.miola.reviews.ReviewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/villes")
public class VilleController {

    @Autowired
    private VilleService villeService;

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private EndroitService endroitService;

    //Afficher tous les villes ou bien une seule avec son nom
    @GetMapping(path = "")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> getAll(@Validated @RequestParam(value="name" , required = false) String name) {
        if (name != null) {
            Optional<VilleModel> ville = villeRepository.findByVillename(name);
            return new ResponseEntity<>(ville.get(), HttpStatus.OK);
        }
        List<VilleModel> list = villeService.getAll();
        ResponseWithArray response = new ResponseWithArray(HttpStatus.OK, ControllerMessages.SUCCESS, list);
        return new ResponseEntity<ResponseWithArray>(response, response.getStatus());
    }

    //Ajouter une ville
    @PostMapping("")
    public ResponseEntity<VilleModel> addCity(@RequestBody VilleModel ville) {
        VilleModel _ville = villeService.save(new VilleModel(ville.getId(), ville.getVillename(), ville.getEndroits()));
        return new ResponseEntity<>(_ville, HttpStatus.CREATED);
    }

    //Ajouter un endroit à une ville
    @PostMapping(path = "/{id}/endroits")
    public ResponseEntity<EndroitModel> addEndroitToCity(@Validated @PathVariable("id") int id,@RequestBody EndroitModel endroit){
        VilleModel ville = villeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found City with id = " + id));
        EndroitModel _endroit = endroitService.save(new EndroitModel(endroit.getId(), endroit.getName(), endroit.getDescription(), endroit.getImage(), ville, endroit.getReviews()));
        return new ResponseEntity<>(_endroit, HttpStatus.CREATED);
        //return new ResponseEntity<>(ville.getEndroits(), HttpStatus.OK);
    }

    //Afficher une ville à partir de son Id
    @GetMapping(path="/{id}")
    public ResponseEntity<VilleModel> getCityById(@PathVariable("id") int id) {
        VilleModel ville = villeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found city with id = " + id));
        return new ResponseEntity<>(ville, HttpStatus.OK);
    }

    //Get All Endroit of a City
    @GetMapping(path = "/{id}/endroits")
    public ResponseEntity<List<EndroitModel>> getEndroitOfCity(@PathVariable("id") int id){
        VilleModel ville = villeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found City with id = " + id));
        return new ResponseEntity<>(ville.getEndroits(), HttpStatus.OK);
    }

    //Mosifier une ville
    @PostMapping(path="/{id}")
    public ResponseEntity<VilleModel> updateCity(@PathVariable("id") int id, @RequestBody VilleModel villeRequest) {
        VilleModel ville = villeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CityId " + id + "not found"));
        ville.setVillename(villeRequest.getVillename());
        return new ResponseEntity<>(villeRepository.save(ville), HttpStatus.OK);
    }

    //Supprimer une ville
    @DeleteMapping(path="/{id}")
    public ResponseEntity<HttpStatus> deleteCity(@PathVariable("id") int id) {
        villeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Supprimer tous les villes
    @DeleteMapping(path="")
    public ResponseEntity<HttpStatus> deleteAllCity() {
        villeRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }







}
