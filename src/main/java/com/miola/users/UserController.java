package com.miola.users;

import com.miola.dto.*;
import com.miola.responseMessages.ControllerMessages;
import com.miola.responseMessages.UtilMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000" , allowCredentials = "true")
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
    public ResponseEntity<ResponseWithToken> login(@RequestBody @Validated LoginRequest loginRequest , HttpServletResponse res) throws Exception {
        String token = userService.login(loginRequest,res);
        ResponseWithToken response = new ResponseWithToken(HttpStatus.OK, ControllerMessages.LOG_IN_SUCCESS_MESSAGE, token);
        ResponseEntity<ResponseWithToken> re = new ResponseEntity<>(response, response.getStatus());
        return re;
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWithOneObject> me(@RequestHeader(value = UtilMessages.AUTHORIZATION) String token) {
        UserDetailsWithoutPwd user = userService.me(token);
        ResponseWithOneObject response = new ResponseWithOneObject(HttpStatus.OK, ControllerMessages.SUCCESS, user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
