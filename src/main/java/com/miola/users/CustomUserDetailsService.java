package com.miola.users;

import com.miola.exceptions.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) {
        Optional<UserModel> userOp = userRepository.findByEmail(email);

        if (!userOp.isPresent()) {
            throw new UserDoesntExistException();
        }

        UserModel user = userOp.get();

        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getAddress(),
                true, true, true, true,
                user.getRoleAsAuthorities()
        );
    }
}
