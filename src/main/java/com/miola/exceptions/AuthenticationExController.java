package com.miola.exceptions;

import com.miola.dto.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication-exception")
public class AuthenticationExController {

    @RequestMapping("")
    public ResponseEntity<BasicResponse> root() {
        throw new GeneralAuthentificationException();
    }

    @GetMapping("/corrupt-token")
    public ResponseEntity<BasicResponse> handleCT() {
        throw new CorruptTokenException();
    }

    @GetMapping("/invalid-token")
    public ResponseEntity<BasicResponse> handleIT() {
        throw new InvalidTokenException();
    }

    @GetMapping("/not-logged-in")
    public ResponseEntity<BasicResponse> handleNL() {
        throw new NotLoggedInOrInvalidAuthorizationHeaderException();
    }

    @GetMapping("/jwt")
    public ResponseEntity<BasicResponse> handleOther() {
        throw new CorruptTokenException();
    }


}
