package in.tech_camp.furima.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemQueryResult {

  private Long id;
  private String img;
  private String name;
  private Long price;
  private Integer deliveryFee;
  private Long itemId;

}