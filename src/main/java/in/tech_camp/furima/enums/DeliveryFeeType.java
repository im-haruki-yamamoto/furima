package in.tech_camp.furima.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryFeeType {

  BUYER_PAYS(1, "着払い (購入者負担)"),
  SELLER_PAYS(2, "送料込み (出品者負担)");

  private final int code;
  private final String label;

  public static DeliveryFeeType fromCode(int code) {
    return Arrays.stream(values())
        .filter(d -> d.getCode() == code).findFirst()
        .orElseThrow(() -> new IllegalArgumentException("存在しない配送料負担コードです" + code));
  }

  public static DeliveryFeeType fromDisplayName(String displayName) {
    for (DeliveryFeeType deliveryFeeType : DeliveryFeeType.values()) {
      if (deliveryFeeType.getLabel().equals(displayName)) {
        return deliveryFeeType;
      }
    }
    throw new IllegalArgumentException("不正な配送料負担表示です: " + displayName);
  }

}