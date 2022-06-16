package com.miola.endroits;


import com.miola.dto.ResponseWithRecordCount;
import com.miola.messages.ControllerMessages;
import com.miola.users.UserModel;
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

    public List<EndroitModel> populateList() {
        List<EndroitModel> dataList = new ArrayList<>();
        dataList.add(new EndroitModel(1,"wad l9annar","Tetouan"));
        dataList.add(new EndroitModel(2,"badis","Nador"));
        dataList.add(new EndroitModel(3,"les jardins","Rabat"));
        return dataList;
    }

//    @GetMapping(path = "")
//    @ResponseStatus(code = HttpStatus.OK)
//    public ResponseEntity<ResponseWithArray> getAll() {
//        List<EndroitModel> list = endroitService.getAll();
//        ResponseWithArray response = new ResponseWithArray(HttpStatus.OK, ControllerMessages.SUCCESS, list);
//        return new ResponseEntity<ResponseWithArray>(response, response.getStatus());
//    }

    @GetMapping
    public ResponseEntity<ResponseWithRecordCount> getAllEndroits(){
        List<EndroitModel> list = endroitService.getAll();
        ResponseWithRecordCount response = new ResponseWithRecordCount(HttpStatus.OK, ControllerMessages.SUCCESS, list.size(), list);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // A voir
    @GetMapping(path = "/{id}")
    public ResponseEntity<EndroitModel> getOne(@PathVariable int id) {
        List<EndroitModel> le = populateList();
        EndroitModel endroit = null;
        for (EndroitModel e : le) {
            if (e.getId() == id) endroit = e;
        }
        return new ResponseEntity<>(endroit, HttpStatus.OK);
    }

    // search endroits by ville
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

    @PostMapping(path = "")
    public ResponseEntity<Object> addEndroit(@Validated @RequestBody EndroitModel endroit) {
        endroitService.addOne(endroit);
        return new ResponseEntity<>(endroit, HttpStatus.CREATED);
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
    }

//    @PostMapping(path = "")
//    public ResponseEntity<Object> addOne(@Validated @RequestBody EndroitModel endroit) {
//        List<EndroitModel> le = new ArrayList<>();
//        le.add(endroit);
//        return new ResponseEntity<>(endroit, HttpStatus.CREATED);
//    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return errors;
//    }
}
