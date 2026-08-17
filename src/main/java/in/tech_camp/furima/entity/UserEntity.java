package in.tech_camp.furima.entity;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

@Data
public class UserEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long id;
  private String nickname;
  private String email;
  private String password;
  private String lastName;
  private String firstName;
  private String lastNameKana;
  private String firstNameKana;
  private LocalDate birthday;

}