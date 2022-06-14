package com.miola.users;

import com.miola.dto.LoginRequest;
import com.miola.dto.SignUpRequest;
import com.miola.exceptions.LoginFailException;
import com.miola.exceptions.PasswordsNotMatchingException;
import com.miola.exceptions.UserAlreadyExistsException;
import com.miola.exceptions.UserDoesntExistException;
import com.miola.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public String login(LoginRequest loginRequest) {

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

        return jwtUtil.generateToken(userDetails);
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

    public void deleteOne(int id) {
        userRepository.deleteById(id);
    }

    public void updateOne(UserModel user) {
        userRepository.save(user);
    }


}
