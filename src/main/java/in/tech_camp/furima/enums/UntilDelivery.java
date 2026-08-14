package in.tech_camp.furima.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UntilDelivery {
    FAST(1, "1〜2日で発送"),
    NORMAL(2, "2〜3日で発送"),
    SLOW(3, "4〜7日で発送");

    private final int code;
    private final String displayName;

    public static UntilDelivery fromCode(int code) {
        for (UntilDelivery until : UntilDelivery.values()) {
            if (until.getCode() == code) {
                return until;
            }
        }
        throw new IllegalArgumentException("不正な発送日数コードです: " + code);
    }

    public static UntilDelivery fromDisplayName(String displayName) {
        for (UntilDelivery until : UntilDelivery.values()) {
            if (until.getDisplayName().equals(displayName)) {
                return until;
            }
        }
        throw new IllegalArgumentException("不正な発送日数表示です: " + displayName);
    }
}