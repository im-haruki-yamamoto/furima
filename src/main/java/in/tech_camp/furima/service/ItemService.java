package in.tech_camp.furima.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import in.tech_camp.furima.dto.ItemListDto;
import in.tech_camp.furima.dto.ItemResponseDto;
import in.tech_camp.furima.entity.ItemEntity;
import in.tech_camp.furima.entity.UserEntity;
import in.tech_camp.furima.enums.DeliveryFeeType;
import in.tech_camp.furima.exception.ForbiddenException;
import in.tech_camp.furima.exception.ResourceNotFoundException;
import in.tech_camp.furima.form.ItemEditForm;
import in.tech_camp.furima.form.ItemForm;
import in.tech_camp.furima.mapper.ItemMapper;
import in.tech_camp.furima.repository.ItemRepository;
import in.tech_camp.furima.repository.UserRepository;
import in.tech_camp.furima.util.SaveImageFileUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

  private final ItemMapper itemMapper;

  private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    // 商品一覧表示機能
    public List<ItemListDto> allItem() {

        List<ItemListDto> items = itemRepository.findAll()
            .stream().map(item -> {
                ItemListDto dto = new ItemListDto();
                dto.setId(item.getId());
                dto.setImg(item.getImg());
                dto.setName(item.getName());
                dto.setPrice(item.getPrice());
                dto.setSoldout(item.getItemId() != null);
                dto.setDeliveryFee(DeliveryFeeType.fromCode(item.getDeliveryFee()).getDisplayName());
                return dto;
            }).collect(Collectors.toList());

        return items;
    }

    // 商品出品機能
    @Transactional
    public void saveItem(ItemForm form, Long userId) throws IOException {
        String imageName = null;
        MultipartFile imgFile = form.getImg();

        if (imgFile != null && !imgFile.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            imageName = uuid + "-" + imgFile.getOriginalFilename();

            Path uploadDir = Paths.get("uploads").toAbsolutePath();

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path imagePath = uploadDir.resolve(imageName);
            Files.copy(imgFile.getInputStream(), imagePath);
        }

        ItemEntity item = new ItemEntity();
        item.setImg(imageName);
        item.setUserId(userId);
        item.setName(form.getName());
        item.setDescription(form.getDescription());
        item.setCategory(form.getCategory());
        item.setCondition(form.getCondition());
        item.setDeliveryFee(form.getDeliveryFee());
        item.setPrefecture(form.getPrefecture());
        item.setUntilDelivery(form.getUntilDelivery());
        item.setPrice(form.getPrice());

        itemRepository.insert(item);
    }

    @Transactional(readOnly = true)
    public ItemResponseDto findById(Long itemId) throws Exception {
        ItemEntity itemEntity = itemRepository.findById(itemId);
        if (itemEntity == null) {
            throw new Exception("該当の商品が見つかりません");
        }
        UserEntity userEntity = userRepository.findById(itemEntity.getUserId());
        return new ItemResponseDto(itemEntity, userEntity);
    }

    @Transactional
    public void deleteItem(Long itemId, Long currentUserId) throws Exception {
        ItemEntity item = itemRepository.findById(itemId);

        if (item == null) {
            throw new Exception("該当の商品が見つかりません");
        }

        if (item.getUserId() == null || !Objects.equals(item.getUserId(), currentUserId)) {
            throw new Exception("削除権限がありません");
        }

        itemRepository.deleteById(itemId);
    }

  // 商品詳細取得
  @Transactional(readOnly = true)
  public ItemResponseDto findById(Long itemId) {
    ItemEntity itemEntity = itemMapper.findById(itemId);
    if (itemEntity == null) {
      throw new ResourceNotFoundException("該当の商品が見つかりません: ID=" + itemId);
    }
    return new ItemResponseDto(itemEntity);
  }

  // 商品編集用データの取得
  @Transactional(readOnly = true)
  public ItemEditForm getItemForEdit(Long itemId, Long currentUserId) {
    ItemEntity item = findItemAndCheckOwner(itemId, currentUserId);
    return ItemConverterService.convertToEditForm(item);
  }

  // 商品更新
  @Transactional
  public void updateItem(Long itemId, ItemEditForm itemForm, Long currentUserId) throws IOException {
    ItemEntity item = findItemAndCheckOwner(itemId, currentUserId);

    item.setName(itemForm.getName());
    item.setDescription(itemForm.getDescription());
    item.setCategory(itemForm.getCategory());
    item.setCondition(itemForm.getCondition());
    item.setDeliveryFee(itemForm.getDeliveryFee());
    item.setPrefecture(itemForm.getPrefecture());
    item.setUntilDelivery(itemForm.getUntilDelivery());
    item.setPrice(itemForm.getPrice());

    MultipartFile image = itemForm.getImg();
    if (image != null && !image.isEmpty()) {
      String fileName = SaveImageFileUtil.saveImageFile(image);
      item.setImg(fileName);
    }

    itemMapper.update(item);
  }

  // 商品削除
  // @Transactional
  // public void deleteItem(Long itemId, Long currentUserId) {
  //   ItemEntity item = itemMapper.findById(itemId);

  //   if (item == null) {
  //     throw new ResourceNotFoundException("該当の商品が見つかりません: ID=" + itemId);
  //   }

  //   if (item.getUser() == null || !Objects.equals(item.getUser().getId(), currentUserId)) {
  //     throw new ForbiddenException("削除権限がありません");
  //   }

  //   itemMapper.deleteById(itemId);
  // }

  // 所有権・売却状態の共通チェック
  private ItemEntity findItemAndCheckOwner(Long itemId, Long currentUserId) {
    ItemEntity item = itemMapper.findById(itemId);
    if (item == null) {
      throw new ResourceNotFoundException("該当の商品が見つかりません: ID=" + itemId);
    }
    if (item.getUserId() == null || !Objects.equals(item.getUserId(), currentUserId)) {
      throw new ForbiddenException("編集権限がありません");
    }
    if (itemMapper.isSoldOut(itemId)) {
      throw new ForbiddenException("売却済みの商品は編集できません");
    }
    return item;
  }


    
}