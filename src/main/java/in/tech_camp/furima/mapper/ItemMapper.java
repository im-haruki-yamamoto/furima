package in.tech_camp.furima.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.furima.dto.ItemQueryResult;
import in.tech_camp.furima.entity.Item;

@Mapper
public interface ItemMapper {

  // 商品出品機能
  @Insert("INSERT INTO items (user_id, name, description, category, condition, delivery_fee, prefecture, until_delivery, price, img) VALUES (#{userId}, #{name}, #{description}, #{category}, #{condition}, #{deliveryFee}, #{prefecture}, #{untilDelivery}, #{price}, #{img})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(Item item);

  // 商品一覧表示機能
  @Select("""
      SELECT i.id, i.img, i.name, i.price, i.delivery_fee, b.item_id FROM items i
      LEFT JOIN orders b
      ON i.id = b.item_id
      ORDER BY i.id DESC
      """)
  List<ItemQueryResult> findAll();

  // 売却済みチェック
  @Select("SELECT COUNT(*) > 0 FROM orders WHERE item_id = #{itemId}")
  boolean isSoldOut(Long itemId);

  // 商品1件を取得
  @Select("""
          SELECT
              i.id AS id,
              i.user_id AS userId,
              i.name AS name,
              i.description AS description,
              i.category AS category,
              i.condition AS condition,
              i.delivery_fee AS deliveryFee,
              i.prefecture AS prefecture,
              i.until_delivery AS untilDelivery,
              i.price AS price,
              i.img AS img,
              u.id AS "user.id",
              u.nickname AS "user.nickname"
          FROM items i
          JOIN users u ON i.user_id = u.id
          WHERE i.id = #{id}
      """)
  Item findById(Long id);


  // 商品削除
  @Delete("DELETE FROM items WHERE id = #{itemId}")
  void deleteById(@Param("itemId") Long id);

}
