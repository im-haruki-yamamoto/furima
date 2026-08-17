package in.tech_camp.furima.dto;

import lombok.Data;

@Data
public class ItemEditDto {
  private Long id;
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
