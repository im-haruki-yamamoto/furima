package in.tech_camp.furima.service;

import in.tech_camp.furima.dto.ItemEditDto;
import in.tech_camp.furima.entity.ItemEntity;

public class ItemConverterService {

  public static ItemEditDto convertToEditDto(ItemEntity item) {
    if (item == null) {
      return null;
    }

    ItemEditDto dto = new ItemEditDto();
    dto.setId(item.getId());
    dto.setName(item.getName());
    dto.setDescription(item.getDescription());
    dto.setCategory(item.getCategory());
    dto.setCondition(item.getCondition());
    dto.setDeliveryFee(item.getDeliveryFee());
    dto.setPrefecture(item.getPrefecture());
    dto.setUntilDelivery(item.getUntilDelivery());
    dto.setPrice(item.getPrice());
    dto.setImg(item.getImg());

    return dto;
  }
}
