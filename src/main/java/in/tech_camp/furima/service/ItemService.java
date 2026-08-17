package in.tech_camp.furima.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import in.tech_camp.furima.dto.ItemListDto;
import in.tech_camp.furima.dto.ItemResponseDto;
import in.tech_camp.furima.entity.Item;
import in.tech_camp.furima.entity.User;
import in.tech_camp.furima.enums.DeliveryFeeType;
import in.tech_camp.furima.form.ItemForm;
import in.tech_camp.furima.mapper.ItemMapper;
import in.tech_camp.furima.mapper.UserMapper;

@Service
public class ItemService {

    private final ItemMapper itemMapper;
    private final UserMapper userMapper;

    public ItemService(ItemMapper itemmapper, UserMapper usermapper) {
        this.itemMapper = itemmapper;
        this.userMapper = usermapper;
    }

    // 商品一覧表示機能
    @Transactional(readOnly = true)
    public List<ItemListDto> allItem() {

        List<ItemListDto> items = itemMapper.findAll()
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

        Item item = new Item();
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

        itemMapper.insert(item);
    }

    // 商品詳細取得
    @Transactional(readOnly = true)
    public ItemResponseDto findById(Long itemId) throws Exception {
        Item item = itemMapper.findById(itemId);
        if (item == null) {
            throw new Exception("該当の商品が見つかりません");
        }
        User user = userMapper.findById(item.getUserId());
        return new ItemResponseDto(item, user);
    }

    // 売却済み判定機能
    @Transactional(readOnly = true)
    public boolean isSold(Long itemId) {
        return itemMapper.isSoldOut(itemId);
    }

    // 商品削除機能
    @Transactional
    public void deleteItem(Long itemId, Long currentUserId) throws Exception {
        Item item = itemMapper.findById(itemId);

        if (item == null) {
            throw new Exception("該当の商品が見つかりません");
        }

        if (item.getUserId() == null || !Objects.equals(item.getUserId(), currentUserId)) {
            throw new Exception("削除権限がありません");
        }

        itemMapper.deleteById(itemId);
    }
}