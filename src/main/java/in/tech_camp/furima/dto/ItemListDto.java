package in.tech_camp.furima.dto;

import lombok.Data;

@Data
public class ItemListDto {
  
  private Long id;
  private String img;
  private String name;
  private Long price;
  private String deliveryFee;
  private boolean soldout;
}
