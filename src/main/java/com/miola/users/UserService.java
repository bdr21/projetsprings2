package com.miola.users;

import com.miola.dto.ContactRequest;
import com.miola.dto.LoginRequest;
import com.miola.dto.SignUpRequest;
import com.miola.dto.UserDetailsWithoutPwd;
import com.miola.exceptions.*;
import com.miola.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void signUp(SignUpRequest signUpRequest) {
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();
        String confirmPassword = signUpRequest.getConfirmPassword();
        String firstName = signUpRequest.getFirstName();
        String lastName = signUpRequest.getLastName();
        String address =  signUpRequest.getAddress();

        boolean isUserExist = userRepository.existsByEmail(signUpRequest.getEmail());

        if (isUserExist) {
            throw new UserAlreadyExistsException();
        }

        if (!password.equals(confirmPassword)) {
            throw new PasswordsNotMatchingException();
        }

        String encodedPassword = passwordEncoder.encode(password);

        UserModel newUser = new UserModel(email, firstName, lastName, encodedPassword, address, "USER");

        userRepository.save(newUser);
    }

    public String login(LoginRequest loginRequest, HttpServletResponse res) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<UserModel> userOp = userRepository.findByEmail(email);

        if (!userOp.isPresent()) {
            throw new UserDoesntExistException();
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (Exception e) {
            System.out.println("==>"+e.getMessage());
            System.out.println("==>"+e.getCause());
            e.printStackTrace();
            throw new LoginFailException();
        }

        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        String token = jwtUtil.generateToken(userDetails);

        Cookie cookie = new Cookie("jwt", token);
        cookie.setMaxAge(10 * 60 * 60);
        res.addCookie(cookie);

        return token;
    }

    public UserDetailsWithoutPwd me(String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String email = userDetails.getEmail();
        String parsedToken = token.substring(7);

        boolean isValidateToken = jwtUtil.validateToken(parsedToken, userDetails);

        if (!isValidateToken) {
            throw new InvalidTokenException();
        }

        Optional<UserModel> userOp = userRepository.findByEmail(email);

        if (userOp.isEmpty()){
            throw new UserDoesntExistException();
        }

        UserModel user = userOp.get();

        UserDetailsWithoutPwd userDetailsWithoutPwd = new UserDetailsWithoutPwd(
            user.getId(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getAddress()
        );

        return userDetailsWithoutPwd;
    }

    public List<UserModel> getAll() {
        return userRepository.findAll();
    }

    public void addOne(UserModel user) {
        userRepository.save(user);
    }

    public Optional<UserModel> getOneById(int id) {
        return userRepository.findById(id);
    }

    public Optional<UserModel> getOneByRole(String role) { return userRepository.findByRole(role); }

    public List<UserModel> getAllByRole(String role) { return userRepository.findAllByRole(role); }

    public void deleteOne(int id) {
        userRepository.deleteById(id);
    }

    public void updateOne(UserModel user) {
        userRepository.save(user);
    }


}
