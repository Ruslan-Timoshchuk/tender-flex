package com.flex.tender.model;

import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class User {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;
    private List<Authority> authorities;

    public List<SimpleGrantedAuthority> getAuthorities() {
        return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getRole())).toList();
    }

}