package in.tech_camp.furima.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

  // 商品1件を取得（@One による User 取得を削除し、SQLエイリアスでマッピング）
  @Select("""
      SELECT 
          id,
          user_id AS userId,
          name,
          description,
          category,
          condition,
          delivery_fee AS deliveryFee,
          prefecture,
          until_delivery AS untilDelivery,
          price,
          img
      FROM items
      WHERE id = #{itemId}
      """)
  Item findById(@Param("itemId") Long itemId);

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
  void update(Item item);

  // 商品削除
  @Delete("DELETE FROM items WHERE id = #{itemId}")
  void deleteById(@Param("itemId") Long id);

}