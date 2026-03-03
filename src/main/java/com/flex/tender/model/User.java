package com.flex.tender.model;

import java.util.Set;
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
    private Set<Authority> authorities;

}