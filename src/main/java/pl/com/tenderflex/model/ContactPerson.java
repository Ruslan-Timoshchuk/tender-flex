package pl.com.tenderflex.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactPerson {

    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;

}