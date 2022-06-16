package com.miola.endroits;


import com.miola.dto.ResponseWithArray;
import com.miola.responseMessages.ControllerMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping(path = "")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ResponseWithArray> getAll() {
        List<EndroitModel> list = endroitService.getAll();
        ResponseWithArray response = new ResponseWithArray(HttpStatus.OK, ControllerMessages.SUCCESS, list);
        return new ResponseEntity<ResponseWithArray>(response, response.getStatus());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EndroitModel> getOne(@PathVariable int id) {
        List<EndroitModel> le = populateList();
        EndroitModel endroit = null;
        for (EndroitModel e : le) {
            if (e.getId() == id) endroit = e;
        }
        return new ResponseEntity<>(endroit, HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<Object> addOne(@Validated @RequestBody EndroitModel endroit) {
        List<EndroitModel> le = new ArrayList<>();
        le.add(endroit);
        return new ResponseEntity<>(endroit, HttpStatus.CREATED);
    }

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
