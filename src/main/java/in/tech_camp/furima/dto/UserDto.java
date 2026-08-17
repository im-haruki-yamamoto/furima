package in.tech_camp.furima.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UserDto {
    private String nickname;
    private String email;
    private String password;
    private String lastName;
    private String firstName;
    private String lastNameKana;
    private String firstNameKana;
    private LocalDate birthDate; 
}