package com.miola.endroit_crud.Controller;


import com.miola.endroit_crud.Models.Endroit;
import com.miola.endroit_crud.Repo.EndroitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/endroits")
public class EndroitController {

    @Autowired
    EndroitRepository endroitRepository;





    // GET /Endroits
    //pour acces tapez 'localhost:8082/api/endroits' without quotes
    @GetMapping("")
    public Iterable<Endroit> findAll(){
        List<Endroit> products = new ArrayList<>();
        Iterator<Endroit> iterator = endroitRepository.findAll().iterator();
        iterator.forEachRemaining(products::add);
        Collections.reverse(products);
        return products;
    }

    // GET /products/5
    @GetMapping("/{id}")
    public ResponseEntity<Endroit> findOne(@PathVariable(value = "id") long id) {
        Endroit product = endroitRepository.findById(id).get();
        if(product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(product);
    }

    // POST /Endroits
    // pour l'acces  tapez 'localhost:8082/api/endroits/'
    @PostMapping("")
    public ResponseEntity<Endroit> create( @RequestBody Endroit product){
        Endroit newProduct = endroitRepository.save(product);
        return ResponseEntity.ok(newProduct);
    }
    // PUT /Endroit/5
    //pour L'acces Tapez 'localhost:8082/api/endroits/'
    @PutMapping("/{id}")

    public String updateEndroit(@PathVariable long id,@RequestBody Endroit endroit)
    {
        Endroit updateendroit=endroitRepository.findById(id).get();
        updateendroit.setName(endroit.getName());
        updateendroit.setDescription(endroit.getDescription());
        updateendroit.setVille(endroit.getVille());
        updateendroit.setName_photo(endroit.getName_photo());
        endroitRepository.save(updateendroit);
        return "Updated.....";
    }


    // DELETE /Endroit/5
    //pour l'acces tapez 'localhost:8082/api/endroits/4'
    @DeleteMapping("/{id}")

    public String deleteEndroit(@PathVariable long id)
    {
        Endroit deleteendroit =endroitRepository.findById(id).get();
        endroitRepository.delete(deleteendroit);
        return "Delete endroit with the id:"+id;
    }


}
