package in.tech_camp.furima.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import in.tech_camp.furima.dto.ItemQueryResult;
import in.tech_camp.furima.entity.ItemEntity;

@Mapper
public interface ItemMapper {

  // 出品
  @Insert("INSERT INTO items (user_id, name, description, category, condition, delivery_fee, prefecture, until_delivery, price, img) VALUES (#{userId}, #{name}, #{description}, #{category}, #{condition}, #{deliveryFee}, #{prefecture}, #{untilDelivery}, #{price}, #{img})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(ItemEntity item);

  // 一覧取得
  @Select("""
      SELECT i.id, i.img, i.name, i.price, i.delivery_fee, b.item_id 
      FROM items i
      LEFT JOIN orders b ON i.id = b.item_id
      ORDER BY i.id DESC
      """)
  List<ItemQueryResult> findAll();

  // 売却済みチェック
  @Select("SELECT COUNT(*) > 0 FROM orders WHERE item_id = #{itemId}")
  boolean isSoldOut(Long itemId);

  // 商品1件取得（usersテーブルをJOINしてニックネームも取得）
  @Select("""
      SELECT i.*, u.nickname AS user_nickname
      FROM items i
      JOIN users u ON i.user_id = u.id
      WHERE i.id = #{itemId}
      """)
  ItemEntity findById(Long itemId);

  // 更新
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

  // 削除
  @Delete("DELETE FROM items WHERE id = #{id}")
  void deleteById(Long id);
}