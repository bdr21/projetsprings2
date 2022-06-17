package com.miola.users;

import com.miola.dto.*;
import com.miola.responseMessages.ControllerMessages;
import com.miola.responseMessages.UtilMessages;
import com.miola.dto.ResponseWithToken;
import com.miola.endroits.EndroitModel;
import com.miola.dto.BasicResponse;
import com.miola.dto.LoginRequest;
import com.miola.dto.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000" , allowCredentials = "true")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;    // it's used to encode the new password of the user

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

    // this method returns the id of the logged-in user
    public int currentUserName(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUserId();
    }

    //get all info about the logged-in user
    @GetMapping("/profile")
    public ResponseEntity<UserModel> profile(){
        Optional<UserModel> userModel = userService.getOneById(currentUserName(SecurityContextHolder.getContext().getAuthentication()));
        return new ResponseEntity<>(userModel.get(), HttpStatus.OK);
    }

    // modify user's info
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Object> updateProfile(@Validated @RequestBody UserModel userInfo){
        // get the logged-in user
        Optional<UserModel> userModel = userService.getOneById(currentUserName(SecurityContextHolder.getContext().getAuthentication()));
        // convert Optional<UserModel> to UserModel
        UserModel user = userModel.get();
        // modify user's info
        user.setEmail(userInfo.getEmail());
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());
        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        user.setAddress(userInfo.getAddress());
        // save the modifications
        userService.updateOne(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // delete a user
    @GetMapping("/delete")
    public HttpStatus delete(){
        // get the logged-in user
        Optional<UserModel> userModel = userService.getOneById(currentUserName(SecurityContextHolder.getContext().getAuthentication()));
        // convert Optional<UserModel> to UserModel
        UserModel user = userModel.get();
        // delete the user
        userService.deleteOne(user.getId());
        return HttpStatus.OK;
    }

}
