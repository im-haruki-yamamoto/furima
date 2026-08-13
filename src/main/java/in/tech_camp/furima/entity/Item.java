package in.tech_camp.furima.entity;

import lombok.Data;

// ダミークラス
@Data
public class Item {
    private Long id;
    private String name;
    private Integer price;
    private Long userId;
    private String imagePath;
}