package in.tech_camp.furima.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    LADIES(1, "レディース"),
    MENS(2, "メンズ"),
    BABY_KIDS(3, "ベビー・キッズ"),
    INTERIOR(4, "インテリア・住まい・小物"),
    BOOKS(5, "本・音楽・ゲーム"),
    HOBBY(6, "おもちゃ・ホビー・グッズ"),
    COSMETICS(7, "コスメ・香水・美容"),
    ELECTRONICS(8, "家電・スマホ・カメラ"),
    SPORTS(9, "スポーツ・レジャー"),
    HANDMADE(10, "ハンドメイド"),
    OTHER(11, "その他");

    private final int code;
    private final String displayName;

    public static Category fromCode(int code) {
        for (Category category : Category.values()) {
            if (category.getCode() == code) {
                return category;
            }
        }
        throw new IllegalArgumentException("不正なカテゴリーコードです: " + code);
    }

    public static Category fromDisplayName(String displayName) {
        for (Category category : Category.values()) {
            if (category.getDisplayName().equals(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("不正なカテゴリー表示です: " + displayName);
    }
}