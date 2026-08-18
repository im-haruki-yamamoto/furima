package in.tech_camp.furima.dto;

import in.tech_camp.furima.entity.ItemEntity;
import in.tech_camp.furima.enums.Category;
import in.tech_camp.furima.enums.Condition;
import in.tech_camp.furima.enums.DeliveryFeeType;
import in.tech_camp.furima.enums.PrefectureType;
import in.tech_camp.furima.enums.UntilDelivery;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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


    public ItemResponseDto(ItemEntity item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.img = item.getImg();
        this.userId = item.getUserId();

        if (item.getCategory() != null) {
            this.categoryText = Category.fromCode(item.getCategory()).getDisplayName();
        }
        if (item.getDeliveryFee() != null) {
            this.deliveryFeeText = DeliveryFeeType.fromCode(item.getDeliveryFee()).getDisplayName();
        }
        if (item.getPrefecture() != null) {
            this.prefectureText = PrefectureType.fromCode(item.getPrefecture()).getDisplayName();
        }
        if (item.getCondition() != null) {
            this.conditionText = Condition.fromCode(item.getCondition()).getDisplayName();
        }
        if (item.getUntilDelivery() != null) {
            this.untilDeliveryText = UntilDelivery.fromCode(item.getUntilDelivery()).getDisplayName();
        }
    }
}