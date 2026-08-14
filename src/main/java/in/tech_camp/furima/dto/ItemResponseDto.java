package in.tech_camp.furima.dto;

import in.tech_camp.furima.entity.ItemEntity;
import in.tech_camp.furima.enums.Category;
import in.tech_camp.furima.enums.Condition;
import in.tech_camp.furima.enums.DeliveryFeeType;
import in.tech_camp.furima.enums.PrefectureType;
import in.tech_camp.furima.enums.UntilDelivery;
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
  private String userNickname;

  public ItemResponseDto(ItemEntity entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.description = entity.getDescription();
    this.price = entity.getPrice();
    this.img = entity.getImg();
    if (entity.getUser() != null) {
            this.userId = entity.getUser().getId();
            this.userNickname = entity.getUser().getNickname();
        }

    this.categoryText = Category.fromCode(entity.getCategory()).getDisplayName();
    this.deliveryFeeText = DeliveryFeeType.fromCode(entity.getDeliveryFee()).getLabel();
    this.prefectureText = PrefectureType.fromCode(entity.getPrefecture()).getLabel();
    this.conditionText = Condition.fromCode(entity.getCondition()).getDisplayName();
    this.untilDeliveryText = UntilDelivery.fromCode(entity.getUntilDelivery()).getDisplayName();
  }
}