package in.tech_camp.furima.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OrderForm {

    @NotBlank(message = "郵便番号を入力してください")
    @Pattern(regexp = "^\\d{3}-\\d{4}$", message = "郵便番号は「3桁ハイフン4桁」の半角数値で入力してください")
    private String postalCode;

    @NotNull(message = "都道府県を選択してください")
    @Min(value = 1, message = "都道府県を選択してください")
    private Integer prefectureId;

    @NotBlank(message = "市区町村を入力してください")
    private String city;

    @NotBlank(message = "番地を入力してください")
    private String address;

    private String building;

    @NotBlank(message = "電話番号を入力してください")
    @Pattern(regexp = "^\\d{10,11}$", message = "電話番号は10桁以上11桁以内の半角数値で入力してください")
    private String phoneNumber;

    @NotBlank(message = "クレジットカード情報を入力してください")
    private String token;
}