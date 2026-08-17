package in.tech_camp.furima.dto;

import lombok.Data;

@Data
public class ItemResponseDto {
    private Long id;
    private String name;
    private String description;
    private String categoryText;
    private String conditionText;
    private String deliveryFeeText;
    private String prefectureText;
    private String untilDeliveryText;
    private Long price;
    private String img;
    private Long userId;
}