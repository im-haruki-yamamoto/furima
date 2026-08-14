package in.tech_camp.furima.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.tech_camp.furima.dto.ItemResponseDto;
import in.tech_camp.furima.entity.ItemEntity;
import in.tech_camp.furima.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public ItemResponseDto findById(Long itemId) throws Exception {
        ItemEntity itemEntity = itemRepository.findById(itemId);
        if (itemEntity == null) {
            throw new Exception("該当の商品が見つかりません");
        }
        return new ItemResponseDto(itemEntity);
    }

    @Transactional
    public void deleteItem(Long itemId, Long currentUserId) throws Exception {
        ItemEntity item = itemRepository.findById(itemId);

        if (item == null) {
            throw new Exception("該当の商品が見つかりません");
        }

        if (item.getUser() == null || !Objects.equals(item.getUser().getId(), currentUserId)) {
            throw new Exception("削除権限がありません");
        }

        itemRepository.deleteById(itemId);
    }
}