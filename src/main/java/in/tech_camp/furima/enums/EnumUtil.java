package in.tech_camp.furima.enums;

import java.util.Arrays;

public class EnumUtil {

    public static <E extends Enum<E> & CodedEnum> E fromCode(E[] values, int code, String enumName) {
        return Arrays.stream(values)
                .filter(e -> e.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("存在しない" + enumName + "コードです: " + code));
    }

    public static <E extends Enum<E> & CodedEnum> E fromDisplayName(E[] values, String displayName, String enumName) {
        return Arrays.stream(values)
                .filter(e -> e.getDisplayName().equals(displayName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("不正な" + enumName + "表示です: " + displayName));
    }
}