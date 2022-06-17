package com.miola.villes;

import com.miola.dto.ResponseWithArray;
import com.miola.endroits.EndroitModel;
import com.miola.exceptions.ResourceNotFoundException;
import com.miola.messages.ControllerMessages;
import com.miola.reviews.ReviewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(path = "")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ResponseWithArray> getAllCity() {
        List<VilleModel> list = villeService.getAll();
        ResponseWithArray response = new ResponseWithArray(HttpStatus.OK, ControllerMessages.SUCCESS, list);
        return new ResponseEntity<ResponseWithArray>(response, response.getStatus());
    }

    @PostMapping("")
    public ResponseEntity<VilleModel> addCity(@RequestBody VilleModel ville) {
        VilleModel _ville = villeService.save(new VilleModel(ville.getId(), ville.getVille_name(), ville.getEndroits()));
        return new ResponseEntity<>(_ville, HttpStatus.CREATED);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<VilleModel> getCityById(@PathVariable("id") int id) {
        VilleModel ville = villeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found city with id = " + id));
        return new ResponseEntity<>(ville, HttpStatus.OK);
    }

    //Get All Endroit of a City
    @GetMapping(path = "/{id}/reviews")
    public ResponseEntity<List<EndroitModel>> getEndroitOfCity(@PathVariable("id") int id){
        VilleModel ville = villeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found City with id = " + id));
        return new ResponseEntity<>(ville.getEndroits(), HttpStatus.OK);
    }


    @PutMapping(path="/{id}")
    public ResponseEntity<VilleModel> updateCity(@PathVariable("id") int id, @RequestBody VilleModel villeRequest) {
        VilleModel ville = villeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CityId " + id + "not found"));
        ville.setVille_name(villeRequest.getVille_name());
        return new ResponseEntity<>(villeRepository.save(ville), HttpStatus.OK);
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<HttpStatus> deleteCity(@PathVariable("id") int id) {
        villeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path="")
    public ResponseEntity<HttpStatus> deleteAllCity() {
        villeRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*@GetMapping(path="/{ville_name}")
    public ResponseEntity<Optional<VilleModel>> getCityByName(@PathVariable("ville_name") String name) {
        Optional<VilleModel> villes = villeRepository.findByVille_name(name);
        if (villes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(villes, HttpStatus.OK);
    }*/




}
