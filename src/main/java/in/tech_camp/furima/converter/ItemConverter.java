package in.tech_camp.furima.converter;

import org.springframework.stereotype.Component;

import in.tech_camp.furima.dto.ItemResponseDto;
import in.tech_camp.furima.entity.Item;
import in.tech_camp.furima.enums.Category;
import in.tech_camp.furima.enums.Condition;
import in.tech_camp.furima.enums.DeliveryFeeType;
import in.tech_camp.furima.enums.PrefectureType;
import in.tech_camp.furima.enums.UntilDelivery;

@Component
public class ItemConverter {

    public ItemResponseDto toDto(Item item) {
        if (item == null) {
            return null;
        }

        ItemResponseDto dto = new ItemResponseDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setPrice(item.getPrice());
        dto.setImg(item.getImg());
        dto.setUserId(item.getUserId());

        // Enumから表示用テキストへの変換ロジック
        dto.setCategoryText(Category.fromCode(item.getCategory()).getDisplayName());
        dto.setDeliveryFeeText(DeliveryFeeType.fromCode(item.getDeliveryFee()).getDisplayName());
        dto.setPrefectureText(PrefectureType.fromCode(item.getPrefecture()).getDisplayName());
        dto.setConditionText(Condition.fromCode(item.getCondition()).getDisplayName());
        dto.setUntilDeliveryText(UntilDelivery.fromCode(item.getUntilDelivery()).getDisplayName());

        return dto;
    }
}