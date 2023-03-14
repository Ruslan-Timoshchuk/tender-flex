package pl.com.tenderflex.model;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Role> roles;

}