package in.tech_camp.furima.dto;

import in.tech_camp.furima.entity.ItemEntity;
import in.tech_camp.furima.entity.UserEntity;
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

  public ItemResponseDto(ItemEntity item, UserEntity user) {
    this.id = item.getId();
    this.name = item.getName();
    this.description = item.getDescription();
    this.price = item.getPrice();
    this.img = item.getImg();
    

    this.userId = item.getUserId();
    

    if (user != null) {
      this.userNickname = user.getNickname();
    }

    this.categoryText = Category.fromCode(item.getCategory()).getDisplayName();
    this.deliveryFeeText = DeliveryFeeType.fromCode(item.getDeliveryFee()).getDisplayName();
    this.prefectureText = PrefectureType.fromCode(item.getPrefecture()).getDisplayName();
    this.conditionText = Condition.fromCode(item.getCondition()).getDisplayName();
    this.untilDeliveryText = UntilDelivery.fromCode(item.getUntilDelivery()).getDisplayName();
  }
}