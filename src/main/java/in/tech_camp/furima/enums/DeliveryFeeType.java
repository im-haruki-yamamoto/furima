package in.tech_camp.furima.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryFeeType implements CodedEnum {

  BUYER_PAYS(1, "着払い (購入者負担)"),
  SELLER_PAYS(2, "送料込み (出品者負担)");

  private final int code;
  private final String displayName;

  public static DeliveryFeeType fromCode(int code) {
        return EnumUtil.fromCode(values(), code, "配送料負担");
    }

  public static DeliveryFeeType fromDisplayName(String displayName) {
        return EnumUtil.fromDisplayName(values(), displayName, "配送料負担");
    }

}