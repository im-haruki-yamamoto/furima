package in.tech_camp.furima.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import in.tech_camp.furima.dto.UserDto;
import lombok.Data;

@Data
public class RegisterForm {
    
    @NotBlank(message = "ニックネームを入力してください")
    private String nickname;

    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "メールアドレスの形式が正しくありません")
    private String email;

    @NotBlank(message = "パスワードを入力してください")
    @Size(min = 6, message = "パスワードは6文字以上で入力してください")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]+$", message = "パスワードは半角英数字混合で設定してください")
    private String password;

    private String passwordConfirmation;

    @NotBlank(message = "姓を入力してください")
    @Pattern(regexp = "^[ぁ-んァ-ヶ一-龥々ー]+$", message = "姓は全角で入力してください")
    private String lastName;

    @NotBlank(message = "名を入力してください")
    @Pattern(regexp = "^[ぁ-んァ-ヶ一-龥々ー]+$", message = "名は全角で入力してください")
    private String firstName;

    @NotBlank(message = "姓(カナ)を入力してください")
    @Pattern(regexp = "^[ァ-ヶー]+$", message = "姓(カナ)は全角カタカナで入力してください")
    private String lastNameKana;

    @NotBlank(message = "名(カナ)を入力してください")
    @Pattern(regexp = "^[ァ-ヶー]+$", message = "名(カナ)は全角カタカナで入力してください")
    private String firstNameKana;

    @NotNull(message = "誕生年を入力してください")
    private Integer birthYear;

    @NotNull(message = "誕生月を入力してください")
    private Integer birthMonth;

    @NotNull(message = "誕生日を入力してください")
    private Integer birthDay;

    public UserDto toDto() {
        UserDto dto = new UserDto();
        dto.setNickname(this.nickname);
        dto.setEmail(this.email);
        dto.setPassword(this.password);
        dto.setLastName(this.lastName);
        dto.setFirstName(this.firstName);
        dto.setLastNameKana(this.lastNameKana);
        dto.setFirstNameKana(this.firstNameKana);
        // 年・月・日を LocalDate にまとめる
        dto.setBirthDate(LocalDate.of(this.birthYear, this.birthMonth, this.birthDay));
        return dto;
    }
}