package in.tech_camp.furima.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import in.tech_camp.furima.dto.ItemEditDto;
import in.tech_camp.furima.entity.ItemEntity;
import in.tech_camp.furima.exception.ForbiddenException;
import in.tech_camp.furima.exception.ResourceNotFoundException;
import in.tech_camp.furima.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {
  private final ItemMapper itemRepository;

// 商品編集
  @Transactional(readOnly = true)
  public ItemEditDto getItemForEdit(Long itemId, Long currentUserId) {
    ItemEntity item = findItemAndCheckOwner(itemId, currentUserId);
    return ItemConverterService.convertToEditDto(item);
  }

  @Transactional
  public ItemEditDto updateItem(Long itemId, ItemEditDto itemForm, MultipartFile image, Long currentUserId) {
    ItemEntity item = findItemAndCheckOwner(itemId, currentUserId);


    item.setName(itemForm.getName());
    item.setDescription(itemForm.getDescription());
    item.setCategory(itemForm.getCategory());
    item.setCondition(itemForm.getCondition());
    item.setDeliveryFee(itemForm.getDeliveryFee());
    item.setPrefecture(itemForm.getPrefecture());
    item.setUntilDelivery(itemForm.getUntilDelivery());
    item.setPrice(itemForm.getPrice());

    if (image != null && !image.isEmpty()) {
      String fileName = SaveImageFileUtil.saveImageFile(image);
      item.setImg(fileName);
    }

    itemRepository.update(item);

    return ItemConverterService.convertToEditDto(item);
  }

  private ItemEntity findItemAndCheckOwner(Long itemId, Long currentUserId) {
    ItemEntity item = itemRepository.findById(itemId);
    if (item == null) {
      throw new ResourceNotFoundException("該当の商品が見つかりません: ID=" + itemId);
    }
    if (item.getUser() == null || !Objects.equals(item.getUser().getId(), currentUserId)) {
      throw new ForbiddenException("編集権限がありません");
    }
    return item;
  }
}