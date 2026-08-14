package in.tech_camp.furima.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import in.tech_camp.furima.entity.ItemEntity;

@Mapper
public interface ItemMapper {

  // 売却済みチェック
  @Select("SELECT COUNT(*) > 0 FROM orders WHERE item_id = #{itemId}")
  boolean isSoldOut(Long itemId);

  // 商品編集
  @Select("SELECT * FROM items WHERE id = #{itemId}")
  ItemEntity findById(Long itemId);

  @Update("""
      UPDATE items SET
              name = #{name},
              description = #{description},
              category = #{category},
              condition = #{condition},
              delivery_fee = #{deliveryFee},
              prefecture = #{prefecture},
              until_delivery = #{untilDelivery},
              price = #{price},
              img = #{img}
              WHERE id = #{id}
          """)
  void update(ItemEntity item);
}
