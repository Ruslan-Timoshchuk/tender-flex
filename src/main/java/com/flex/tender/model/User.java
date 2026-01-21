package com.flex.tender.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flex.tender.model.enumeration.EAuthority;
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

    public List<EAuthority> getAuthorityTitles() {
        return authorities.stream().map(Authority::getTitle).toList();
    }

}