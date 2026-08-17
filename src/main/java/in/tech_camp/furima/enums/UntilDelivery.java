package in.tech_camp.furima.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UntilDelivery implements CodedEnum {
    FAST(1, "1〜2日で発送"),
    NORMAL(2, "2〜3日で発送"),
    SLOW(3, "4〜7日で発送");

    private final int code;
    private final String displayName;

    public static UntilDelivery fromCode(int code) {
        return EnumUtil.fromCode(values(), code, "発送日の目安");
    }

    public static UntilDelivery fromDisplayName(String displayName) {
        return EnumUtil.fromDisplayName(values(), displayName, "発送日数");
    }
}