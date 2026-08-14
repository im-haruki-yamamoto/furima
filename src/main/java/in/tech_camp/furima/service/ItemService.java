package in.tech_camp.furima.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.tech_camp.furima.dto.ItemResponseDto;
import in.tech_camp.furima.entity.Item;
import in.tech_camp.furima.mapper.ItemMapper;
import in.tech_camp.furima.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;
    private final OrderMapper orderMapper;

    @Transactional(readOnly = true)
    public ItemResponseDto findById(Long itemId) throws Exception {
        Item item = itemMapper.findById(itemId);
        if (item == null) {
            throw new Exception("該当の商品が見つかりません");
        }
        return new ItemResponseDto(item);
    }

    // 購入済みかを判定するメソッドを追加
    @Transactional(readOnly = true)
    public boolean isSold(Long itemId) {
        return orderMapper.existsByItemId(itemId);
    }

    @Transactional
    public void deleteItem(Long itemId, Long currentUserId) throws Exception {
        Item item = itemMapper.findById(itemId);

        if (item == null) {
            throw new Exception("該当の商品が見つかりません");
        }

        if (item.getUser() == null || !Objects.equals(item.getUser().getId(), currentUserId)) {
            throw new Exception("削除権限がありません");
        }

        itemMapper.deleteById(itemId);
    }
}