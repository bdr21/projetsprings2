package com.miola.users;

import com.miola.reviews.ReviewModel;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String address;

    @Pattern(regexp = "(USER|ADMIN)" , message = "role is not valid")
    private String role;

    public UserModel (
            String email,
            String firstName,
            String lastName,
            String password,
            String address,
            String role
    ){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    public UserModel(String email, String firstName, String lastName, String password, String address) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.address = address;
    }

    public List<SimpleGrantedAuthority> getRoleAsAuthorities() {
        List<SimpleGrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority(this.role)
        );
        return authorities;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ReviewModel> reviews;
}
