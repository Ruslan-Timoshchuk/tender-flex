package pl.com.tenderflex.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Token {

  private Integer id;
  private User user;
  private String jwtToken;
  private TokenType tokenType;
  private boolean revoked;
  private boolean expired;
 
}