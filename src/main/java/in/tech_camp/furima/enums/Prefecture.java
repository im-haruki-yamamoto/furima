package in.tech_camp.furima.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// ダミークラス
@Getter
@RequiredArgsConstructor
public enum Prefecture {
    NONE(0, "--"),
    HOKKAIDO(1, "北海道"),
    TOKYO(13, "東京都");

    private final int id;
    private final String name;
}