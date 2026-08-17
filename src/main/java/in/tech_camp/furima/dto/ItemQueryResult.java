package in.tech_camp.furima.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ItemQueryResult {

  private Long id;
  private String img;
  private String name;
  private Long price;
  private int deliveryFee;
  private Long itemId;

}
