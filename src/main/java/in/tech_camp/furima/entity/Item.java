package in.tech_camp.furima.entity;

import lombok.Data;

@Data
public class Item {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private Integer category;
    private Integer condition;
    private Integer deliveryFee;
    private Integer prefecture;
    private Integer untilDelivery;
    private Long price;
    private String img;
}