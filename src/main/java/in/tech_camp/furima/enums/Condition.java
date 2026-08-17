package in.tech_camp.furima.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Condition implements CodedEnum {
    NEW(1, "新品・未使用"),
    LIKE_NEW(2, "未使用に近い"),
    GOOD(3, "目立った傷や汚れなし"),
    FAIR(4, "やや傷や汚れあり"),
    POOR(5, "傷や汚れあり"),
    BAD(6, "全体的に状態が悪い");

    private final int code;
    private final String displayName;

    public static Condition fromCode(int code) {
        return EnumUtil.fromCode(values(), code, "商品の状態");
    }

    public static Condition fromDisplayName(String displayName) {
        return EnumUtil.fromDisplayName(values(), displayName, "商品の状態");
    }
}