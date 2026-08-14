package in.tech_camp.furima.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.tech_camp.furima.entity.ItemEntity;
import in.tech_camp.furima.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemMapper itemMapper;

    @Transactional
    public void deleteItem(Long itemId, Long currentUserId) throws Exception {

        ItemEntity item = itemMapper.findById(itemId);
        if (item == null) {
            throw new Exception("該当の商品が見つかりません");
        }

        if (item.getUser() == null || !Objects.equals(item.getUser().getId(), currentUserId)) {
            throw new Exception("削除権限がありません");
        }

        itemMapper.deleteById(itemId);
    }
}
