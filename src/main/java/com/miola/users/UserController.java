package com.miola.users;

import com.miola.dto.*;
import com.miola.messages.ControllerMessages;
import com.miola.messages.UtilMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<BasicResponse> signUp(@RequestBody @Validated SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest);

        BasicResponse response = new BasicResponse(HttpStatus.OK, ControllerMessages.SIGN_UP_SUCCESS_MESSAGE);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseWithToken> login(@RequestBody @Validated LoginRequest loginRequest) throws Exception {
        String token = userService.login(loginRequest);
        ResponseWithToken response = new ResponseWithToken(HttpStatus.OK, ControllerMessages.LOG_IN_SUCCESS_MESSAGE, token);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWithOneObject> me(@RequestHeader(value = UtilMessages.AUTHORIZATION) String token) {
        UserDetailsWithoutPwd user = userService.me(token);
        ResponseWithOneObject response = new ResponseWithOneObject(HttpStatus.OK, ControllerMessages.SUCCESS, user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
