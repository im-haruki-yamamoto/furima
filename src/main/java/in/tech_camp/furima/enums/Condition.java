package in.tech_camp.furima.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Condition {
    NEW(1, "新品・未使用"),
    LIKE_NEW(2, "未使用に近い"),
    GOOD(3, "目立った傷や汚れなし"),
    FAIR(4, "やや傷や汚れあり"),
    POOR(5, "傷や汚れあり"),
    BAD(6, "全体的に状態が悪い");

    private final int code;
    private final String displayName;

    public static Condition fromCode(int code) {
        for (Condition condition : Condition.values()) {
            if (condition.getCode() == code) {
                return condition;
            }
        }
        throw new IllegalArgumentException("不正な商品状態コードです: " + code);
    }

    public static Condition fromDisplayName(String displayName) {
        for (Condition condition : Condition.values()) {
            if (condition.getDisplayName().equals(displayName)) {
                return condition;
            }
        }
        throw new IllegalArgumentException("不正な商品状態表示です: " + displayName);
    }
}