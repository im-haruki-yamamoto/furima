package in.tech_camp.furima.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OrderForm {

    @NotBlank(message = "は必須項目です")
    @Pattern(regexp = "^\\d{3}-\\d{4}$", message = "は「3桁ハイフン4桁」の半角数値で入力してください")
    private String postalCode;

    @NotNull(message = "を選択してください")
    @Min(value = 1, message = "を選択してください")
    private Integer prefectureId;

    @NotBlank(message = "は必須項目です")
    private String city;

    @NotBlank(message = "は必須項目です")
    private String address;

    // 建物名は任意
    private String building;

    @NotBlank(message = "は必須項目です")
    @Pattern(regexp = "^\\d{10,11}$", message = "は10桁以上11桁以内の半角数値で入力してください")
    private String phoneNumber;

    @NotBlank(message = "を入力してください")
    private String token;
}